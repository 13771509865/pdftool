package com.neo.service.file;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.commons.util.UUIDHelper;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * @author zhoufeng
 * @description
 * @create 2019-11-25 13:54
 **/
@Service("saveBadFileService")
public class SaveBadFileService {

    public IResult<Integer> saveBadFile(String srcDir, String destDir, String srcRelativePath) {
        try {
            File srcFile = new File(srcDir, srcRelativePath);
            if (srcFile.isFile() && srcFile.exists()) {
                File errorFile = new File(destDir + File.separator + DateViewUtils.format(new Date(), "yyyy/MM/dd"), srcFile.getName());
                if (errorFile.isFile() && errorFile.exists()) {
                    File parentFile = errorFile.getParentFile();
                    errorFile = new File(parentFile, UUIDHelper.generateUUID() + "-" + srcFile.getName());
                }
                FileUtils.copyFile(srcFile, errorFile);
                return DefaultResult.successResult();
            }
            return DefaultResult.failResult("错误文件不存在");
        } catch (Exception e) {
            SysLogUtils.error("拷贝转码失败文件异常", e);
            return DefaultResult.failResult("记录错误文件失败");
        }
    }
}
