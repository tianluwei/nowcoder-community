package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("request或name参数为空！");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
//                    我在这里打印了一下，但结果为null
//                    System.out.println(cookie.getDomain());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void removeExpiredCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("ticket")){
                cookie.setMaxAge(0);
            }
        }
    }
}
