package com.neo.model.bo;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhoufeng
 * @description view模块Token对象
 * @create 2019-03-20 13:32
 **/
@ApiModel(value = "viewTokenBO",description = "文档视图token对象")
public class ViewTokenBO {
    @ApiModelProperty(value = "文档fileHash或本地模式下传生成文档相对路径")
    @NotBlank(message = "fileHash不能为空")
    private String fileHash;

    @ApiModelProperty(value = "过期时间,单位秒,小于等于0表示永久",example="-1")
    private Long time = -1L;

    @ApiModelProperty(value = "允许次数,小于等于0表示不限制",example="-1")
    private Long num = -1L;

    @ApiModelProperty(value = "tokenId",hidden = true)
    private String tokenId;

    @ApiModelProperty(value = "是否允许下载")
    private Boolean allowDownload=true;

    @ApiModelProperty(value = "是否允许预览")
    private Boolean allowPreview=true;

    @ApiModelProperty(value = "本地cache模式下使用,传转换类型",example="0")
    private Integer convertType; //本地cache模式下使用

    @ApiModelProperty(value = "异步预览要用到的",hidden = true)
    private String convertId;

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Boolean getAllowDownload() {
        return allowDownload;
    }

    public void setAllowDownload(Boolean allowDownload) {
        this.allowDownload = allowDownload;
    }

    public Boolean getAllowPreview() {
        return allowPreview;
    }

    public void setAllowPreview(Boolean allowPreview) {
        this.allowPreview = allowPreview;
    }

    public Integer getConvertType() {
        return convertType;
    }

    public void setConvertType(Integer convertType) {
        this.convertType = convertType;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

 

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
