package com.neo.commons.cons;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoufeng
 * @description 事件枚举
 * @create 2019-11-14 08:58
 **/
@Getter
public enum EnumEventType {

    /**
     * 各种事件
     */
    CONVERT_EVENT(1, "yozo.yocloud.pdf.convert", new HashMap<String, Object>() {{
        put("comment", "PDF文件转换");
    }});

    private EnumEventType(Integer value, String typeId, Map<String, Object> data) {
        this.value = value;
        this.typeId = typeId;
        this.data = data;
    }

    private Integer value;

    private String typeId;

    private Map<String, Object> data;

    public static EnumEventType getEnum(Integer value) {
        if (value != null) {
            for (EnumEventType event : values()) {
                if (value.equals(event.getValue())) {
                    return event;
                }
            }
        }
        return null;
    }
}
