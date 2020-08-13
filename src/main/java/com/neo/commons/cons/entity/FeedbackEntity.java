package com.neo.commons.cons.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FeedbackEntity对象", description = "反馈请求参数对象")
public class FeedbackEntity {

	@ApiModelProperty("反馈意见内容")
	@NotBlank(message = "请填写反馈意见")
	private String content;

	@ApiModelProperty("联系方式")
	private String contactMode;

}
