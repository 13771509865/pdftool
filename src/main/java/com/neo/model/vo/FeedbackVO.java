package com.neo.model.vo;

import com.alibaba.fastjson.JSON;
import com.neo.model.bo.UserBO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackVO {

	private Integer code;
	
	private String msg;
	
	private String askId;
	
	
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
	
}
