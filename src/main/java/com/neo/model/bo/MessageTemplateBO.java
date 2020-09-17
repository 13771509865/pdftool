package com.neo.model.bo;


import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageTemplateBO {

    private String templateId;
    private Map param;
    private String sign;
    private String nonce;
    private List<String> userIds;
    private List<String> depIds;
    private List<String> corpIds;
    private List<String> phones;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
