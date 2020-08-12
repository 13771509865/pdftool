package com.neo.commons.cons.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {
	
	
	@NotBlank(message = "请填写反馈意见")
	private String content;
	
	
	private String contactMode;

}
