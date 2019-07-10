package com.neo.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {

    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
             digital = bytes[i];
 
            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    
    /**
     * @param message 待加密数据
     * @return 加密后数据
     * @description 生成md5
     */
    //TODO 全部大写了这边不应该这样
    public static String getMD5(String message) {
        return getMD5(message, false);
    }

    public static String getMD5(InputStream is) throws IOException {
        return getMD5(is, false);
    }

    /**
     * @param upperCase 是否大写
     * @description 生成md5
     * @author zhoufeng
     * @date 2019/5/9
     */
    public static String getMD5(String message, Boolean upperCase) {
        String md5 = DigestUtils.md5Hex(message).toUpperCase();
        return upperCase ? md5.toUpperCase() : md5;
    }

    public static String getMD5(InputStream is, Boolean upperCase) throws IOException {
        String md5 = DigestUtils.md5Hex(is).toUpperCase();
        return upperCase ? md5.toUpperCase() : md5;
    }
    
    public static void main(String[] args) {

        System.out.println(MD5Utils.getMD5("123456"));
        System.out.println(MD5Utils.getMD5("加密"));
        //E10ADC3949BA59ABBE56E057F20F883E
        //56563EDF23B9D717DC63981B8836FC60
    }
}