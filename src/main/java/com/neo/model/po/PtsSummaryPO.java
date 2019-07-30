package com.neo.model.po;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsSummaryPO {

	private Integer id;
	private Integer zeroToThree;
	private Integer threeToFive;
	private Integer fiveToTen;
	private Integer tenToFifteen;
	private Integer fifteenToTwenty;
	private Integer twentyToThirty;
	private Integer thirtyToFourty;
	private Integer fourtyToFifty;
	private Integer fiftyMore;
	private String  ipAddress;
	private Integer isSuccess;
	private Integer appType;
	private Date createDate;
	private Date createTime;
	private Date modifiedDate;
	private Date modifiedTime;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
