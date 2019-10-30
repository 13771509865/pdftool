package com.neo.model.bo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhoufeng
 * @description 特性Bo
 * @create 2019-10-30 16:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureBo {

    private String type;

    private String name;

    private String version;

    private String comment;

    private List<FeatureDetailBo> features;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
