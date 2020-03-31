package com.neo.commons.util;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;

/**
 * @author zhoufeng
 * @description zip工具类
 * @create 2019-03-19 13:27
 **/
public class ZipUtils {
    private final static String separator = File.separator;

    /**
     * @param zipFile   生成的压缩包路径
     * @param inputFile 待压缩的目录
     * @description 压缩文件
     * @author zhoufeng
     * @date 2019/3/19
     */
    public static IResult<String> zipFile(File zipFile, File inputFile) {
        File parentFile = zipFile.getParentFile();
        try {
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            zip(zipFile.getAbsolutePath(), inputFile);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.error("创建zip文件失败", e);
            if (parentFile.exists()) {
                parentFile.delete();
            }
            return DefaultResult.failResult("打包压缩包失败");
        }
        return DefaultResult.successResult();
    }

    private static void zip(String zipFilePath, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)));
        zip(out, inputFile, inputFile.getName());
        out.close(); // 输出流关闭
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + separator)); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + separator + fl[i].getName()); // 递归遍历子文件夹
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                out.write(b); // 将字节流写入当前zip目录
            }
            out.setEncoding("utf-8");
            out.flush();
            bi.close();
            in.close(); // 输入流关闭
        }
    }
}
