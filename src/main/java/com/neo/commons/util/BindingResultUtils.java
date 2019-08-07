package com.neo.commons.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @description
 * @create 2019-04-18 14:22
 **/
public class BindingResultUtils {

    /**
     * @description 从bindingResult获取所有messages
     * @date 2019/4/18
     */
    public static String getMessages(BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
            String message = "";
            for (ObjectError error : allErrors) {
                message += error.getDefaultMessage();
            }
            return message;
    	}
        return null;
    }
    
    
    /**
     * 从bindingResult中随机获取一条信息
     * @param bindingResult
     * @return
     */
    public static String getMessage(BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		return bindingResult.getFieldError().getDefaultMessage();
    	}
    	return null;
    }
    
    
}
