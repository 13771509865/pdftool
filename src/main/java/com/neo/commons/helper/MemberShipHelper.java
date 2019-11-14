package com.neo.commons.helper;

import com.neo.commons.cons.EnumEventType;
import com.neo.commons.cons.constants.MemberShipConsts;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.commons.util.UUIDHelper;
import com.neo.model.bo.MemberShipBo;
import com.neo.service.httpclient.HttpAPIService;
import com.yozosoft.util.SecretSignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhoufeng
 * @description 会员helper
 * @create 2019-11-14 08:48
 **/
@Component
public class MemberShipHelper {

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private HttpAPIService httpAPIService;

    public void addMemberEvent(Long userId, EnumEventType enumEventType) {
        if (userId != null && enumEventType != null) {
            MemberShipBo memberShipBo = new MemberShipBo();
            memberShipBo.setId(UUIDHelper.generateUUID());
            memberShipBo.setTypeId(enumEventType.getTypeId());
            memberShipBo.setUserId(userId);
            memberShipBo.setData(enumEventType.getData());
            memberShipBo.setCreateTime(DateViewUtils.getNowLong());
            memberShipBo.setApp(MemberShipConsts.APP);

            String nonce = UUIDHelper.generateUUID();
            try {
                String sign = SecretSignatureUtils.hmacSHA256(nonce, ptsProperty.getMember_hamc_key());
                String url = ptsProperty.getMembership_url() + "?nonce=" + sign + "&sign=" + sign;
                httpAPIService.doPostByJson(url, memberShipBo.toString());
            } catch (Exception e) {
                SysLogUtils.error("发送会员事件失败", e);
            }
        }
    }
}
