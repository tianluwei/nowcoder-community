package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger log= LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT="***";

    private TrieNode rootNode=new TrieNode();

    @PostConstruct
    public void init(){
        try (
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-word.txt");
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String keyword="";
            while((keyword=reader.readLine())!=null){
//                将读出来的关键字加到前缀树
                this.addKeyword(keyword);
            }
        }catch (IOException e){
            log.error("加载敏感词文件失败："+e.getMessage());
        }
    }

    /**
     * 返回过滤敏感词后的文本
     * @param test
     * @return
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
//        指针1
        TrieNode tempNode=rootNode;
//        指针2
        int begin=0;
//        指针3
        int position=0;

        StringBuilder sb=new StringBuilder();
        while(position<text.length()){
            char c=text.charAt(position);
//            跳过符号
            if(isSymbol(c)){
//                若指针1处于根节点，将此符号计入结果，让指针2向下走一步。
                if(tempNode==rootNode){
                    sb.append(c);
                    begin++;
                }
//                无论符号在开头还是结尾，指针3都往下走一步。
                position++;
                continue;
            }
//            检查下级节点。（如果不是符号）
            tempNode=tempNode.getSubNode(c);
            if(tempNode==null){
//                以begin开头的字符串不是敏感词。
                sb.append(text.charAt(begin));
//                进入下一位置
                position=++begin;
//                重新指向根节点
                tempNode=rootNode;
            }else if(tempNode.isKeyWordEnd()){
//                发现敏感词，将begin~position字符串替换掉
                sb.append(REPLACEMENT);
//                进入下一位置
                begin=++position;
//                重新指向根节点
                tempNode=rootNode;
            }else{
//                检查下一个字符
                position++;
            }
        }
//        将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

//    判断是否为符号
    private boolean isSymbol(Character c){
//        0x2E80~0x9FFF是东亚文字范围。
        return !CharUtils.isAsciiAlphanumeric(c) && (c<0x2E80 || c>0x9FFF);
    }

    private void addKeyword(String s){
        TrieNode tempNode=rootNode;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            TrieNode subNode = rootNode.getSubNode(c);
            if(subNode==null){
                subNode=new TrieNode();
                rootNode.addSubNode(c,subNode);
            }

//            指向子节点，进入下一层循环。
            tempNode=subNode;
//            设置结束标识
            if(i==s.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
    }

//    前缀树
    private class TrieNode{
//        关键字结束标识
        private boolean isKeyWordEnd=false;

//        子节点（key是下级字符，value是下级节点）
        private Map<Character,TrieNode> subNodes=new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }

//        添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }

//        获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }

}
