package com.neo.model.bo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xujun
 * @Date: 2020/7/6 2:28 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PtsAuthNameBO",description = "特性注册对象")
public class PtsAuthNameBO {

    @ApiModelProperty(value = "authName")
    private String  authName;

    @ApiModelProperty(value = "authCode")
    private String  authCode;

    @ApiModelProperty(value = "类型",example="Number")
    private String  valueType;

    @ApiModelProperty(value = "单位",example="MB")
    private String  valueUnit;

    @ApiModelProperty(value = "默认值",example="5")
    private String  defaultVaule;

    @ApiModelProperty(value = "说明",example="pdf转word功能")
    private String  description;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
