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
    private Integer convert001Size = 20;

    @NotBlank
    private Integer convert002Size = 20;

    @NotBlank
    private Integer convert003Size = 5;

    @NotBlank
    private Integer convert004Size = 20;

    @NotBlank
    private Integer convert005Size = 20;

    @NotBlank
    private Integer convert006Size = 20;

    @NotBlank
    private Integer convert007Size = 5;

    @NotBlank
    private Integer convert008Size = 5;

    @NotBlank
    private Integer convert009Size = 5;

    @NotBlank
    private Integer convert010Size = 5;

    @NotBlank
    private Integer convert011Size = 5;

    @NotBlank
    private Integer convert012Size = 5;

    @NotBlank
    private Integer convert013Size = 5;

    @NotBlank
    private Integer convert014Size = 5;

    @NotBlank
    private Integer convert015Size = 20;

    @NotBlank
    private Integer convert016Size = 20;

    @NotBlank
    private Integer convert017Size = 20;

    @NotBlank
    private Integer convert018Size = 20;

    @NotBlank
    private Integer convert019Size = 20;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }




}
