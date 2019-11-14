package com.neo.model.bo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhoufeng
 * @description 会员积分对象
 * @create 2019-11-14 08:53
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberShipBo {

    private String id;

    private String typeId;

    private Long userId;

    private Long createTime;

    private String app;

    private Map<String, Object> data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
