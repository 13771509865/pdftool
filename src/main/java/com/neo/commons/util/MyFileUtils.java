package com.neo.commons.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

public class MyFileUtils {

    public static File reNameFile(File oldFile, String newName) {
        String oldPath = oldFile.getAbsolutePath();
        String ext = FilenameUtils.getExtension(oldPath);
        File newFile = new File(oldFile.getParent(), newName + "." + ext);
        Boolean result = oldFile.renameTo(newFile);
        if (result) {
            return newFile;
        } else {
            return null;
        }
    }

    public static void mkFile(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    public static Boolean mkParentDirs(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        File parentFile = new File(path).getParentFile();
        if (!parentFile.exists()) {
            return parentFile.mkdirs();
        }
        return true;
    }

    public static boolean isExists(File file) {
        if (file.exists()) {
            if (file.isDirectory() && file.list().length <= 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isExists(String root, String path) {
        if (StringUtils.isEmpty(root) || StringUtils.isEmpty(path)) {
            return false;
        } else {
            File file = new File(root, path);
            return isExists(file);
        }
    }

    /**
     * @param dir 删除的根目录
     * @return void
     * @description 递归删除目录下所有文件
     * @author zhoufeng
     * @date 2019/2/11
     */
    public static void deleteDir(File dir) {
        FileUtils.deleteQuietly(dir);
    }

    public static void main(String[] args) {
        File dir = new File("E:\\dcs\\test\\a.txt");
        FileUtils.deleteQuietly(dir);
    }
}
