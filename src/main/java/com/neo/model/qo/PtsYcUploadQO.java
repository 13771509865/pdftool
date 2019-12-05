package com.neo.model.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtsYcUploadQO {
    //主键
    private Integer  id;
    //重试状态(0:失败,1:成功,2:不重试)
    private Integer status;

    private String flag;

    private List ids;
}
