package com.neo.commons.cons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResultEntity {
    // 响应码
    private Integer code;

    // 响应体
    private String body;
    
    
   
    
    
}
