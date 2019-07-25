package com.neo.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.neo.commons.util.UrlEncodingUtils;


/**
 *
 * @author xujun
 * 2019-07-23
 */
@Aspect
@Component
public class HttpAPIServiceAspect {

    @Pointcut(value = "execution(* com.neo.service.httpclient.HttpAPIService.getFileHeaderBOByHead(..))")
    public void getFileHeaderBOByHead() {
    }

    @Before(value = "getFileHeaderBOByHead()")
    public void getFileHeaderBOByHeadBefore(JoinPoint joinPoint) {
    	
        Object[] args = joinPoint.getArgs();
        args[0] = UrlEncodingUtils.encodeUrl(args[0].toString());  //url进行encode编码,不然httpclient可能请求失败
    }
    
  
    
}
