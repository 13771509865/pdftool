package com.neo.model.bo;

import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserBO {
	
	private String id;
	private String role;
	private List<CheckUserBO> members;
	
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
	
	
	
	
	
	
	
	
	
	
	
	

}
