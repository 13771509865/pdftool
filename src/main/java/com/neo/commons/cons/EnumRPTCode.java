package com.neo.commons.cons;

import lombok.Getter;

/**
 * 是否使用资源包次数转码
 * @Author: xujun
 * @Date: 2020/7/28 7:43 下午
 */
@Getter
public enum EnumRPTCode {

    UN_RPT(0, "非资源包次数"),
    IS_RPT(1, "是资源包次数");


    private Integer value;
    private String info;

    private EnumRPTCode(int code, String info) {
        this.value = code;
        this.info = info;
    }


}
