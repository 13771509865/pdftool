package com.neo.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadBO {
	
	private String data;
	
	private Long srcFileSize;
	
	private Integer successNum;
	
	private Integer failNum;
	
	private Integer count;

}
