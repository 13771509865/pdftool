package com.neo.model.qo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xujun
 * @Date: 2020/7/29 4:03 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PtsTotalConvertRecordQO {

    private Integer convertNum;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
