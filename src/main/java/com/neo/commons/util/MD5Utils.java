package com.neo.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

import com.twmacinta.util.MD5;

public class MD5Utils {
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
        String md5 = DigestUtils.md5Hex(message);
        return upperCase ? md5.toUpperCase() : md5;
    }

    public static String getMD5(InputStream is, Boolean upperCase) throws IOException {
        String md5 = DigestUtils.md5Hex(is);
        return upperCase ? md5.toUpperCase() : md5;
    }

    /**
     * @param file file
     * @description fastmd5生成md5
     * @author dh
     * @date 2019/12/16
     */
	public static String  getMD5(File file) throws IOException {
		    String hash = MD5.asHex(MD5.getHash(file));
		    return hash;
	}
}