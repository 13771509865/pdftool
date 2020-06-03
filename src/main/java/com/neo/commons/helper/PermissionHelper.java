package com.neo.commons.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.util.JsonUtils;
import com.neo.model.dto.PermissionDto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoufeng
 * @description 权限helper
 * @create 2019-10-30 10:39
 **/
@Component
public class PermissionHelper {

    public void correctPermission(PermissionDto permissionDto) throws Exception{
        if(permissionDto == null){
            permissionDto = new PermissionDto();
        }
        Class<? extends PermissionDto> permissionDtoClass = permissionDto.getClass();
        Field[] fs = permissionDtoClass.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            //设置属性是可以访问的
            f.setAccessible(true);
            String fieldName = f.getName();
            Class<?> type = f.getType();
            //首字母大写
            String temp = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
            Method setMethod = permissionDtoClass.getDeclaredMethod("set"+temp, type);
            Method getMethod = permissionDtoClass.getDeclaredMethod("get"+temp);
            Object fValue = getMethod.invoke(permissionDto);
            if(fValue == null){
                EnumAuthCode authCode = EnumAuthCode.getEnum(fieldName);
                if(authCode!=null){
                    setMethod.invoke(permissionDto, authCode.getDefaultVaule());
                }
            }
        }
    }

    /**
     * 构建默认权限
     * @author xujun
     * @date 2019/11/1
     */
    public PermissionDto buildDefaultPermission() throws Exception {
        PermissionDto permissionDto = new PermissionDto();
        correctPermission(permissionDto);
        return permissionDto;
    }

    public static void main(String[] args) throws Exception {

    }
}
