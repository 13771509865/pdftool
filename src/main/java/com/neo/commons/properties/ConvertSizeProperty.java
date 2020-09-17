package com.neo.commons.properties;


import com.alibaba.fastjson.JSON;
import com.neo.config.YamlPropertySourceFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/**
 * 注册用户每日转换个数配置类
 * @author xujun
 * @description
 * @create 2020年3月9日
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource(value = "classpath:auth.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "convert-size")
public class ConvertSizeProperty {

    @NotBlank
    private Integer convert001Size;

    @NotBlank
    private Integer convert002Size;

    @NotBlank
    private Integer convert003Size;

    @NotBlank
    private Integer convert004Size;

    @NotBlank
    private Integer convert005Size;

    @NotBlank
    private Integer convert006Size;

    @NotBlank
    private Integer convert007Size;

    @NotBlank
    private Integer convert008Size;

    @NotBlank
    private Integer convert009Size;

    @NotBlank
    private Integer convert010Size;

    @NotBlank
    private Integer convert011Size;

    @NotBlank
    private Integer convert012Size;

    @NotBlank
    private Integer convert013Size;

    @NotBlank
    private Integer convert014Size;

    @NotBlank
    private Integer convert015Size;

    @NotBlank
    private Integer convert016Size;

    @NotBlank
    private Integer convert017Size;

    @NotBlank
    private Integer convert018Size;

    @NotBlank
    private Integer convert019Size;

    @NotBlank
    private Integer convert020Size;

    @NotBlank
    private Integer convert021Size;

    @NotBlank
    private Integer convert022Size;

    @NotBlank
    private Integer convert023Size;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }




}
