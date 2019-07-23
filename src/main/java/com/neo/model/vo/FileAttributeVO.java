package com.neo.model.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author zhoufeng
 * @description 文档属性VO
 * @create 2019-04-04 09:05
 **/
public class FileAttributeVO {

    private Integer pageCount;        //总页数

    private String content;          //文本内容

    public FileAttributeVO(){}

    public FileAttributeVO(Integer pageCount, String content){
        this.pageCount = pageCount;
        this.content = content;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
