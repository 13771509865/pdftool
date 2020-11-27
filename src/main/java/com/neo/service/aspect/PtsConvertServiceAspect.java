package com.neo.service.aspect;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.file.SaveBadFileService;
import com.neo.service.message.IMessageService;
import com.neo.service.yzcloud.IYzcloudService;
import com.yozosoft.auth.client.security.UaaToken;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 转换后的文档数据存储
 *
 * @author xujun
 * @description
 * @create 2019年11月25日
 */
//@Aspect
//@Component
public class PtsConvertServiceAspect {

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private SaveBadFileService saveBadFileService;

    @Autowired
    private IYzcloudService iYzcloudService;

    @Autowired
    private IMessageService iMessageService;


    @Pointcut(value = "execution(* com.neo.service.convert.PtsConvertService.dispatchConvert(..))")
    public void dispatchConvert() {
    }

    @AfterReturning(value = "dispatchConvert()&& args(convertBO,uaaToken,convertEntity)", returning = "result")
    public void dispatchConvertAfter(ConvertParameterBO convertBO,UaaToken uaaToken,ConvertEntity convertEntity, IResult<FcsFileInfoBO> result) {
        //转换失败则放入指定文件夹中
        if (!result.isSuccess()) {
            saveBadFileService.saveBadFile(ptsProperty.getFcs_srcfile_dir(), ptsProperty.getConvert_fail_dir(), convertBO,result);
        } else {
            if (uaaToken != null) {
                FcsFileInfoBO fcsFileInfoBO = result.getData();
                String cookie = convertEntity.getCookie();

                //优云app做特殊处理，token拼接
                if(StringUtils.isBlank(cookie) && StringUtils.isNotBlank(convertEntity.getAccessToken()) && StringUtils.isNotBlank(convertEntity.getRefreshToken())){
                    String accessToken = UaaConsts.ACCESS_TOKEN+"="+convertEntity.getAccessToken()+"; ";
                    String refreshToken = UaaConsts.REFRESH_TOKEN+"="+convertEntity.getRefreshToken()+"; ";
                    cookie = accessToken+refreshToken;
                }
                //上传优云
                IResult<String> uploadFileToYc = iYzcloudService.uploadFileToYc(fcsFileInfoBO, uaaToken.getUserId(), cookie);
                //记录上传优云结果
                iYzcloudService.recordFailYCUpload(uploadFileToYc,fcsFileInfoBO, uaaToken.getUserId(), convertEntity);
                //转换成功消息推送
                iMessageService.sendMessageTemplate(uaaToken);
            }
        }
    }
}
