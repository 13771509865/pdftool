package com.neo.model.qo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsSummaryQO extends PageQO{
	
	private Integer id;
	private String  ipAddress;
	private Integer isSuccess;
	private Integer appType;
	private Date showDateStart;
	private Date showDateEnd;
	private String order;

}
