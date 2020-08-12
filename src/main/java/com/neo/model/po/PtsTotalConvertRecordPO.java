package com.neo.model.po;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xujun
 * @Date: 2020/7/29 3:40 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PtsTotalConvertRecordPO {

    private Integer id;
    private Date    createDate;
    private Date    createTime;
    private Date    modifiedDate;
    private Date    modifiedTime;
    private Integer status;
    private Long    userID;
    private String  authCode;
    private Integer convertNum;
    private String  remark;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
