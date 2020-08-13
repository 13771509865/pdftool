package com.neo.model.bo;

import com.alibaba.fastjson.JSON;
import com.neo.model.vo.FileAttributeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "FcsFileInfoBO对象", description = "返回转换结果参数对象")
public class FcsFileInfoBO {
	
    @ApiModelProperty(value = "文件的MD5，hash值")
    private String fileHash = "";

    @ApiModelProperty(value = "文件转换，返回结果码，0表示成功，非0表示失败",example = "0")
    private Integer code = 0;

    @ApiModelProperty(value = "生成文件名")
    private String destFileName = "";

    @ApiModelProperty(value = "源文件名称")
    private String srcFileName = "";

    @ApiModelProperty(value = "源文件大小",example = "0")
    private long srcFileSize = 0;

    @ApiModelProperty(value = "生成文件大小",example = "0")
    private long destFileSize = 0;

    @ApiModelProperty(value = "文件转换类型",example = "0")
    private Integer convertType;

    @ApiModelProperty(value = "源文件路径")
    private String srcStoragePath = "";

    @ApiModelProperty(value = "生成文件路径")
    private String destStoragePath = "";

    @ApiModelProperty(value = "转码时长",example = "0")
    private Long convertTime;

    @ApiModelProperty(value = "文档属性")
    private FileAttributeVO fileAttributeVO;

    @ApiModelProperty(value = "预览地址")
    private String viewUrl;

    @ApiModelProperty(value = "fcs自定义数据")
    private String fcsCustomData;

    @ApiModelProperty(value = "文件转换，返回结果信息")
    private String fcsMessage;

    @ApiModelProperty(value = "上传优云，返回结果信息")

    private String ycMessage;

    @ApiModelProperty(value = "上传优云，返回结果码，0表示成功，非0表示失败", example = "0")
    private Integer ycErrorCode;
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
