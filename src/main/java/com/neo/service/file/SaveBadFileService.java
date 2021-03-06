package com.neo.service.file;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.commons.util.UUIDHelper;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.auth.impl.AuthManager;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthManager authManager;


    public IResult<Integer> saveBadFile(String srcDir, String destDir, ConvertParameterBO convertBO,IResult<FcsFileInfoBO> result) {
        try {
            File srcFile = new File(srcDir, convertBO.getSrcRelativePath());
            if (srcFile.isFile() && srcFile.exists()) {
                String authCode = authManager.getAuthCode(convertBO);
                String errorDir = destDir + File.separator + DateViewUtils.format(new Date(), "yyyy/MM/dd")+ File.separator +authCode+File.separator+result.getData().getCode();
                File errorFile = new File(errorDir, srcFile.getName());
                if (errorFile.isFile() && errorFile.exists()) {
                    File parentFile = errorFile.getParentFile();
                    errorFile = new File(parentFile, UUIDHelper.generateUUID() + "-" + srcFile.getName());
                }
                FileUtils.copyFile(srcFile, errorFile);
                return DefaultResult.successResult();
            }
            return DefaultResult.failResult(EnumResultCode.E_ERROR_FILE_NULL.getInfo());
        } catch (Exception e) {
            SysLogUtils.error("拷贝转码失败文件异常", e);
            return DefaultResult.failResult(EnumResultCode.E_RECORD_FILE_ERROR.getInfo());
        }
    }
}
