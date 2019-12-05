package com.neo.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsYcUploadPO {
    //主键
    private Integer  id;
    //创建时间
    private Date     gmtCreate;
    //修改时间
    private Date     gmtModified;
    //文件hash值
    private String   fileHash;
    //生成文件路径
    private String   destStoragePath;
    //用户Id
    private Long     userId;
    //重试状态(0:失败,1:成功,2:不重试)
    private Integer  status;
    //转换类型
    private Integer  convertType;

    private String   cookie;
    //最终文件名
    private String   srcFileName;
}
