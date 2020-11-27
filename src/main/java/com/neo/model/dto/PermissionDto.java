package com.neo.model.dto;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限对象
 * @author xujun
 * @description
 * @create 2019年11月3日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

	private String convert001;
	private String convert002;
	private String convert003;
	private String convert004;
	private String convert005;
	private String convert006;
	private String convert007;
	private String convert008;
	private String convert009;
	private String convert010;
	private String convert011;
	private String convert012;
	private String convert013;
	private String convert014;
	private String convert015;
	private String convert016;
	private String convert017;
	private String convert018;
	private String convert019;
	private String convert020;
	private String convert021;
	private String convert022;
	private String convert023;
	private String convert024;
	private String convert025;
	private String convert026;
	private Integer validityTime;


	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}	


public static void main(String[] args) {
	String auth = "uploadSize:100,convert003:true,convert004:true,convert005:true,convert006:true,convert007:true,convert008:true,convert009:true,convert010:true,convert011:true,convert001:true,convert012:true,convert002:true,convert013:true,convertNum:999999,";
	
}


}
