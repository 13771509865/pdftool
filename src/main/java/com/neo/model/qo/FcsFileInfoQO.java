package com.neo.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FcsFileInfoQO对象", description = "查询用户转换记录参数请求对象")
public class FcsFileInfoQO extends PageQO{

	@ApiModelProperty(value = "id",example = "0")
	private Integer id;

	@ApiModelProperty(value = "IP地址")
	private String ipAddress;

	@ApiModelProperty(value = "文件的MD5，hash值")
	private String fileHash;

	@ApiModelProperty(value = "用户id",example = "0")
	private Long userID;

	@ApiModelProperty(value = "开始时间")
	private Date showDateStart;

	@ApiModelProperty(value = "结束时间")
    private Date showDateEnd;

	@ApiModelProperty(value = "状态，1：正常，2：禁用",example = "0")
    private Integer status;

	@ApiModelProperty(value = "是否使用过资源包,0:未使用，1：使用",example = "0")
    private Integer isRPT;
	


}
