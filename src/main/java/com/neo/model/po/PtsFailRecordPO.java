package com.neo.model.po;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 记录转换失败文件对象
 * @Author: xujun
 * @Date: 2020/7/1 1:45 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsFailRecordPO {

    private Integer id;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer status;
    private Long userId;
    private Integer resultCode;
    private String resultMessage;
    private String srcFileName;
    private Long srcFileSize;
    private Integer convertType;
    private Integer module;
    private String  remark;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
