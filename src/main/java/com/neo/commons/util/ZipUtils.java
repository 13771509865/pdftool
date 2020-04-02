package com.neo.commons.util;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;

/**
 * @author zhoufeng
 * @description zip工具类
 * @create 2019-03-19 13:27
 **/
public class ZipUtils {
    private final static String separator = File.separator;

    public static IResult<String> newZipFile(File zipFile, File srcFile) {
        try {
            if (!srcFile.exists()) {
                return DefaultResult.failResult(EnumResultCode.E_ZIPPACK_FAIL.getInfo());
            }
            Project proj = new Project();
            FileSet fileSet = new FileSet();
            fileSet.setProject(proj);
            // 判断是目录还是文件
            if (srcFile.isDirectory()) {
                fileSet.setDir(srcFile);
            } else {
                fileSet.setFile(srcFile);
            }
            Zip zip = new Zip();
            zip.setProject(proj);
            zip.setDestFile(zipFile);
            zip.addFileset(fileSet);
            zip.setEncoding(SysConstant.CHARSET);
            zip.execute();
            return DefaultResult.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            return DefaultResult.failResult(EnumResultCode.E_ZIPPACK_FAIL.getInfo());
        }
    }

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
            return DefaultResult.failResult(EnumResultCode.E_ZIPPACK_FAIL.getInfo());
        }
        return DefaultResult.successResult();
    }

    private static void zip(String zipFilePath, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)));
        out.setEncoding("GBK");
        zip(out, inputFile, inputFile.getName());
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                ZipEntry zipEntry = new ZipEntry(base + separator);
                zipEntry.setUnixMode(755);
                out.putNextEntry(zipEntry); // 创建zip压缩进入点base
                out.closeEntry();
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + separator + fl[i].getName()); // 递归遍历子文件夹
            }
        } else {
            ZipEntry zipEntry = new ZipEntry(base);
            zipEntry.setUnixMode(644);
            out.putNextEntry(zipEntry); // 创建zip压缩进入点base
            FileInputStream in = new FileInputStream(f);
            byte[] bi = new byte[1024 * 4];
            int length = 0;
            while ((length = in.read(bi)) != -1) {
                out.write(bi, 0, length);
            }
            in.close(); // 输入流关闭
            out.closeEntry();
        }
    }

    public static Boolean unzip(File zipFile, File destFile) {
        try {
            if (!zipFile.exists()) {
                return false;
            }
            Project proj = new Project();
            Expand expand = new Expand();
            expand.setProject(proj);
            expand.setTaskType("unzip");
            expand.setTaskName("unzip");
            expand.setEncoding(SysConstant.CHARSET);
            expand.setSrc(zipFile);
            expand.setDest(destFile);
            expand.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        File file = new File("D:/fcs/a.zip");
        File filea = new File("D:/fcs/log/");
        IResult<String> stringIResult = zipFile(file, filea);
        System.out.println("end");
    }
}
