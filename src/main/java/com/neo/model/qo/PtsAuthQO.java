package com.neo.model.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 查询pts_auth表条件对象
 * @author xujun
 * @description
 * @create 2020年4月3日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsAuthQO {
	
	public Long userid;
	
	public Integer status;
	
	public String authCode;
	 

}
