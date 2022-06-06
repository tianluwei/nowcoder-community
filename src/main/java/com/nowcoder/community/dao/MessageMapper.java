package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> selectConversations(int userId, int offset, int limit);

//    查询当前用户的会话数量
    int selectConversationCount(int userId);

    List<Message> selectLetters(String conversationId,int offset,int limit);

//    查询某个会话所包含的私信数量
    int selectLettersCount(String conversationId);

//    查询未读私信的数量
    int selectLetterUnread(int userId,String conversationId);

//    新增一个消息
    int insertMessage(Message message);

//    修改消息状态，设置已读或删除
    int updateStatus(List<Integer> ids,int status);
}
