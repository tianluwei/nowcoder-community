package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.domain}")
    String domain;

    @Value("${server.servlet.context-path}")
    String contextPath;

    @Value("${community.path.upload}")
    String upload;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @LoginRequired
    @RequestMapping(path = "setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

//    上传头像请求
    @LoginRequired
    @RequestMapping(path = "upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile header, Model model) {
        if (header == null) {
            model.addAttribute("error", "文件不能为空，请重新选择！");
            return "/site/setting";
        }
        // ok,已解决：TODO: 2022/5/20 可能会报这个错：The field header exceeds its maximum permitted size of 1048576 bytes.
//        配置一下他的最大文件。
        String fileName = header.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix) || !(suffix.equals(".png") || suffix.equals(".jpeg") || suffix.equals(".jpg"))) {
            model.addAttribute("error", "文件格式不正确，请重新上传！");
            return "/site/setting";
        }
        fileName = CommunityUtil.generateUUID() + suffix;
        File dest = new File(upload + "/" + fileName);


        try {
            header.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败:" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器异常！", e);
        }

//        更新当前用户的头像路径。（web访问路径）
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeaderUrl(user.getId(), headerUrl);
        System.out.println("跳转到index页面看看能不能打印出列表");

        // TODO: 重要：2022/5/20 这样写会直接跳转index页面，而不是经过/index请求。
        return "redirect:/index";
    }

//    获取头像请求
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void header(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = upload + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("image/" + suffix);
//        输入流赋值给输出流
//        int len=0;
//        byte[] b=new byte[1024];
//        while(true){
//            len=is.read(b);
//            if(len==-1){
//                break;
//            }
//            outputStream.write(b,0,len);
//        }
//        响应图片
        try (
                ServletOutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(fileName);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }

        } catch (IOException e) {
            log.error("读取头像失败", e.getMessage());
        }

    }

}