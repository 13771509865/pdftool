package com.neo.model.po;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcsFileInfoPO {
	
	private Integer id;
	private String ipAddress;
	private Long userID;
	private String fileHash;
	private Integer resultCode;
	private String destFileName;
	private String srcFileName;
	private Long srcFileSize;
	private Long destFileSize;
	private Integer convertType;
	private String srcStoragePath;
	private String destStoragePath;
	private String viewUrl;
	private Date gmtcreate;
	private Date gmtmodified;
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
