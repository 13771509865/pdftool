package com.neo.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhoufeng
 * @description 特性详细Bo
 * @create 2019-10-30 16:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDetailBo {

    private String id;

    private String name;

    private String desc;

    private String valueType;

    private String valueUnit;

    private String minValue;

    private String maxValue;

    private String defaultValue;

    private List<String> optionals;
}
