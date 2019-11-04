package com.neo;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.neo.commons.cons.EnumAuthCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBO {
	@Value(value = "#{EnumAuthCode.PTS_UPLOAD_SIZE.getDefaultVaule()}")
	private Integer uploadSize;
	
	@Value(value = "#{EnumAuthCode.PTS_CONVERT_NUM.getDefaultVaule()}")
	private Integer convertNum;
	
	@Value(value = "#{EnumAuthCode.PDF_WORD.getDefaultVaule()}")
	private String convert001;
	
	@Value(value = "#{EnumAuthCode.WORD_PDF.getDefaultVaule()}")
	private String convert002;
	   @Override
	    public String toString() {
	        return JSON.toJSONString(this);
	    }
	
}
