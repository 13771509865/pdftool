package com.neo.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * 获取文件md5
 * @author zhouf
 * @create 2018-12-14 11:47
 *
 */
public class GetFileMd5Utils {
    //获取文件的md5
    public static String getFileMD5(File file) {
        if (file.isDirectory()) {
            long lastModified = file.lastModified();
            long size = FileUtils.sizeOfDirectory(file);
            String name = file.getName();
            String dirMD5 = name + size + lastModified;
            return "d" + MD5Utils.getMD5(dirMD5);
        } else if (file.isFile()) {
//            FileInputStream in = null;
//            try {
//                in = new FileInputStream(file);
//                return "f" + MD5Utils.getMD5(in);
//            } catch (Exception e) {
//                e.printStackTrace();
//                SysLogUtils.error("获取" + file.getAbsolutePath() + "文件MD5失败");
//                return null;
//            } finally {
//                IOUtils.closeQuietly(in);
//            }
            try {
				return "f" + MD5Utils.getMD5(file);
			} catch (IOException e) {
              e.printStackTrace();
              SysLogUtils.error("获取" + file.getAbsolutePath() + "文件MD5失败");
              return null;
			}

        } else {
            return null;
        }
    }


    public static String getFileMD5(MultipartFile multipartFile) {
        if (multipartFile != null) {
            try (InputStream inputStream = multipartFile.getInputStream()) {
                String md5 = MD5Utils.getMD5(inputStream);
                return "f"+md5;
            } catch (IOException e) {
                SysLogUtils.error("获取MultipartFile MD5值失败");
            }
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File("E:/dcs/output");
        System.out.println(getFileMD5(file));
    }

}
