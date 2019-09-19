package com.neo.model.po;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.util.HttpUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 功能应用统计对象
 * @author xujun
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsApplyPO {
	
	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer module;
	private String address;
	private String fileName;
	private Long fileSize;
	
	
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
	

}
