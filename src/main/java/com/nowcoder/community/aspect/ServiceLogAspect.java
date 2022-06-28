package com.nowcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger log= LoggerFactory.getLogger(ServiceLogAspect.class);

//    声明切入点
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
//        用户[ip.ip.ip.ip],在[时间]访问了[com.nowcoder.community.service.xxx()]
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target= joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        log.info(String.format("用户[%s]，在[%s]，访问了[%s]",ip,now,target));


        String remoteAddr = request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        String remoteUser = request.getRemoteUser();

//        addr:127.0.0.1        port=58875/58799        user=null
//        System.out.println("Addr="+remoteAddr+"；port="+remotePort+"；user="+remoteUser);

    }
}
