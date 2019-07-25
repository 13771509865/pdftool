package com.neo.commons.util;


/**
 * 生成UUID的辅助类
 *
 * @author manjun.han
 * @date 2014.5.12
 */
import java.util.UUID;

public final class UUIDHelper {


    /**
     * 生成随机的32位长的小写的UUID字符串
     *
     * @date 2014.5.12
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 生成随机的32位长的UUID字符串
     *
     * @param upperCase uuid字符串是否是大写
     * @return
     */
    public static String generateUUID(boolean upperCase){
        String uuid = generateUUID() ;
        return upperCase == true ? uuid.toUpperCase() : uuid ;
    }
    
    

}
