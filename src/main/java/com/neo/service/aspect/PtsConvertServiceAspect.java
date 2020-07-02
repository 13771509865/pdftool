package com.neo.service.aspect;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.file.SaveBadFileService;
import com.neo.service.yzcloud.IYzcloudService;
import com.yozosoft.auth.client.security.UaaToken;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 转换后的文档数据存储
 *
 * @author xujun
 * @description
 * @create 2019年11月25日
 */
@Aspect
@Component
public class PtsConvertServiceAspect {

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private SaveBadFileService saveBadFileService;

    @Autowired
    private IYzcloudService iYzcloudService;


    @Pointcut(value = "execution(* com.neo.service.convert.PtsConvertService.dispatchConvert(..))")
    public void dispatchConvert() {
    }

    @AfterReturning(value = "dispatchConvert()&& args(convertBO,uaaToken,convertEntity)", returning = "result")
    public void dispatchConvertAfter(ConvertParameterBO convertBO,UaaToken uaaToken,ConvertEntity convertEntity, IResult<FcsFileInfoBO> result) {
        //转换失败则放入指定文件夹中
        if (!result.isSuccess()) {
            saveBadFileService.saveBadFile(ptsProperty.getFcs_srcfile_dir(), ptsProperty.getConvert_fail_dir(), convertBO);
        } else {
            if (uaaToken != null) {
                //上传文件到优云,更新数据库,未登录用户和企业用户不上传 
                FcsFileInfoBO fcsFileInfoBO = result.getData();
                IResult<String> uploadFileToYc = iYzcloudService.uploadFileToYc(fcsFileInfoBO, uaaToken.getUserId(), convertEntity.getCookie());
            }
        }
    }
}
