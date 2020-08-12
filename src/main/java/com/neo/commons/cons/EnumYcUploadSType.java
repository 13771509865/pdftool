package com.neo.commons.cons;

import lombok.Getter;

/**
 * 记录上传优云文件状态
 * @Author: xujun
 * @Date: 2020/8/6 3:31 下午
 */
@Getter
public enum EnumYcUploadSType {

    E_FALE(0,"失败"),
    E_SUCCESS(1,"成功"),
    E_NO_RETRY(2,"不重试");

    private Integer value;
    private String info;



    private EnumYcUploadSType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }
}
