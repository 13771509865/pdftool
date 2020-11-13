package com.neo.model.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 查询pts_auth_corp表条件对象
 * @author xujun
 * @description
 * @create 2020年4月3日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsAuthCorpQO {
	
	public Long corpId;
	
	public Integer status;
	
	public String authCode;
	 

}
