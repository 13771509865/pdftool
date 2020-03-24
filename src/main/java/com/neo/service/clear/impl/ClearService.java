package com.neo.service.clear.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.properties.PtsProperty;
import com.neo.model.dto.UserClearDto;
import com.neo.model.dto.UserClearRequestDto;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.auth.IAuthService;
import com.neo.service.clear.IClearService;
import com.neo.service.convert.PtsConvertService;
import com.yozosoft.util.SecretSignatureUtils;


@Service
public class ClearService implements IClearService{
	

	@Autowired
	private PtsProperty ptsProperty;
	
	@Autowired
	private IAuthService iAuthService;
	
	@Autowired
	private PtsConvertService ptsConvertService;
	
	
	/**
	 * 签名校验
	 */
    @Override
    public IResult<Integer> checkClearSign(String nonce, String sign) {
        try {
            String signature = SecretSignatureUtils.hmacSHA256(nonce, ptsProperty.getProduct_hmac_key());
            if (!sign.equals(signature)) {
                return DefaultResult.failResult(EnumResultCode.E_USER_CLEAR_ILLEGAL.getInfo(), EnumResultCode.E_USER_CLEAR_ILLEGAL.getValue());
            }
            return DefaultResult.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            return DefaultResult.failResult(EnumResultCode.E_USER_CLEAR_ILLEGAL.getInfo(), EnumResultCode.E_USER_CLEAR_ILLEGAL.getValue());
        }
    }
	
    
    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public IResult<Integer> clearUserData(UserClearRequestDto userClearRequestDto) {
    	if(StringUtils.isNotBlank(userClearRequestDto.getId())) {
    		Long userId = Long.valueOf(userClearRequestDto.getId());
        	FcsFileInfoQO fcsFileInfoQO = new FcsFileInfoQO();
        	fcsFileInfoQO.setUserID(userId);
        	
        	PtsAuthPO ptsAuthPO =new PtsAuthPO();
        	ptsAuthPO.setUserid(userId);
        	
        	ptsConvertService.deletePtsConvert(fcsFileInfoQO);
        	iAuthService.deletePtsAuth(ptsAuthPO);
        	
        	//删企业成员记录
        	if(userClearRequestDto.getMembers()!=null && userClearRequestDto.getMembers().length > 0) {
        		for(UserClearDto userClearDto : userClearRequestDto.getMembers()) {
        			if(StringUtils.isNotBlank(userClearRequestDto.getId())) {
        				userId = Long.valueOf(userClearDto.getId());
            			fcsFileInfoQO.setUserID(userId);
            			ptsAuthPO.setUserid(userId);
            			ptsConvertService.deletePtsConvert(fcsFileInfoQO);
                    	iAuthService.deletePtsAuth(ptsAuthPO);
        			}
        		}
        	}
    	}
    	return DefaultResult.successResult();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
