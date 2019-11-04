package com.neo.service.aspect;

import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.commons.cons.IResult;
import com.neo.commons.helper.PermissionHelper;
import com.neo.commons.util.JsonUtils;
import com.neo.model.dto.PermissionDto;

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
    
    @Pointcut(value = "execution(* com.neo.service.auth.impl.AuthManager.getPermission(..))")
    public void getPermission() {
    }

    @AfterReturning(value = "getPermission() && args(userID)", returning = "result")
    public void getPermissionAfter(Long userID, IResult<Map<String,Object>> result) throws Exception {
    	System.out.println("AuthServiceAspect进入aop");
        if (!result.isSuccess()) {
        	PermissionDto permissionDto =  permissionHelper.buildDefaultPermission();
        	Map<String,Object> permissionDtoAuthMap = JsonUtils.parseJSON2Map(permissionDto);
        	result.setData(permissionDtoAuthMap);
        }
    }
}
