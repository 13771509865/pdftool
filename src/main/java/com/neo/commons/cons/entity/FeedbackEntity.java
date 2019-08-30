package com.neo.commons.cons.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {
	
	
	@NotBlank(message = "请填写反馈意见")
	public String content;
	
	
	public String contactMode;

}
