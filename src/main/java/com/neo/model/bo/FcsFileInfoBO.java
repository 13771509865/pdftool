package com.neo.model.bo;

import com.alibaba.fastjson.JSON;
import com.neo.model.vo.FileAttributeVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcsFileInfoBO {
	
    //文件的md5 hash值
    private String fileHash = "";

    //文件转换结果
    private Integer code = 0;

    //生成文件名
    private String destFileName = "";

    //原文件名
    private String srcFileName = "";

    //原文件大小
    private long srcFileSize = 0;

    //生成文件大小
    private long destFileSize = 0;

    //文件转换类型
    private Integer convertType;

    //原文件路径
    private String srcStoragePath = "";

    //生成文件路径
    private String destStoragePath = "";

    //转码时长
    private Long convertTime;

    //文档属性
    private FileAttributeVO fileAttributeVO;

    //预览地址
    private String viewUrl;

    //fcs自定义数据
    private String fcsCustomData;
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
