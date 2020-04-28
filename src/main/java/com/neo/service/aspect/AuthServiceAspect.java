package com.neo.service.aspect;

import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.commons.cons.IResult;
import com.neo.commons.helper.PermissionHelper;
import com.neo.commons.properties.ConvertNumProperty;
import com.neo.commons.util.JsonUtils;
import com.neo.model.dto.PermissionDto;
import com.neo.service.auth.impl.AuthManager;

/**
 * @author xujun
 * @description AuthServiceAspect aop
 * @create 2019-10-30 13:11
 **/
@Aspect
@Component
public class AuthServiceAspect {

    @Autowired
    private PermissionHelper permissionHelper;
    
    @Autowired
    private AuthManager authManager;
    
    @Autowired
    private ConvertNumProperty convertNumProperty;
    
    @Pointcut(value = "execution(* com.neo.service.auth.impl.AuthManager.getPermission(..))")
    public void getPermission() {
    }

    @AfterReturning(value = "getPermission() && args(userID,authCode)", returning = "result")
    public void getPermissionAfter(Long userID,String authCode, IResult<Map<String,Object>> result) throws Exception {
        if (!result.isSuccess()) {
        	System.out.println("验证权限有问题，进入aop进行权限默认值设定");
        	PermissionDto permissionDto =  permissionHelper.buildDefaultPermission();
        	Map<String,Object> permissionDtoAuthMap = JsonUtils.parseJSON2Map(permissionDto);
        	Map<String,Object> map = JsonUtils.parseJSON2Map(convertNumProperty);
        	permissionDtoAuthMap.putAll(map);
        	result.setData(permissionDtoAuthMap);
        }
    }
}
