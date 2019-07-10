package com.neo.commons.cons.entity;


/**
 * @author zhoufeng
 * @description 文件头信息对象
 * @create 2019-04-11 09:23
 **/

public class FileHeaderEntity {

    private Long contentLength;

    private String lastModified;

    private String fileName;

    private String url;
    
    

    public FileHeaderEntity() {
		super();
	}



	public FileHeaderEntity(Long contentLength, String lastModified, String fileName, String url) {
		super();
		this.contentLength = contentLength;
		this.lastModified = lastModified;
		this.fileName = fileName;
		this.url = url;
	}



	public Long getContentLength() {
		return contentLength;
	}



	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}



	public String getLastModified() {
		return lastModified;
	}



	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	@Override
    public String toString() {
        return url + fileName + contentLength + lastModified;
    }
}
