package com.neo.model.dto;
import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoufeng
 * @description 用户注销请求dto对象
 * @create 2020-02-28 08:57
 **/
@Data
@NoArgsConstructor
public class UserClearRequestDto {

    private String id;

    private String role;

    private UserClearDto[] members;
    
}
