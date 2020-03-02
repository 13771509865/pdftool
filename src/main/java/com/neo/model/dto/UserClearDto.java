package com.neo.model.dto;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoufeng
 * @description
 * @create 2020-02-28 09:02
 **/
@Data
@NoArgsConstructor
public class UserClearDto {

    private String id;

    private String role;
    
}
