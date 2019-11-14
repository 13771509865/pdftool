package com.neo.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.interceptor.ConvertInterceptor;
import com.neo.interceptor.FeedBackInterceptor;
import com.neo.interceptor.UaaAuthInterceptor;
import com.neo.interceptor.UploadInterceptor;

@Configuration
@EnableWebMvc
public class SpringMVCConfigure implements WebMvcConfigurer{
	
	@Autowired
	private UploadInterceptor uploadInterceptor;
	
	@Autowired
	private ConvertInterceptor convertInterceptor;
	
	@Autowired
	private UaaAuthInterceptor uaaAuthInterceptor;
	
	@Autowired
	private FeedBackInterceptor feedBackInterceptor;
	
	
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        //第一个方法设置访问路径前缀，第二个方法设置资源路径
	        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	        // 解决 SWAGGER 404报错
	        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }

	    /**
	     * @description 配置拦截器
	     * @author xujun
	     * @date 2019-07-17
	     */
	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(uaaAuthInterceptor).addPathPatterns("/**").excludePathPatterns("/manager/**/*.*");
	        registry.addInterceptor(uploadInterceptor).addPathPatterns("/file/defaultUpload");
	        registry.addInterceptor(convertInterceptor).addPathPatterns("/composite/**");
	        registry.addInterceptor(feedBackInterceptor).addPathPatterns("/feedback");
	    }

	    /**
	     * @description 允许跨域
	     * @author xujun
	     * @date 2019-07-17
	     */
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").allowCredentials(true).maxAge(3600);
	    }

	    @Override
	    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	        configurer.favorPathExtension(false); //忽略后缀匹配
	    }

	    //设置首页等
	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        //registry.addViewController("/").setViewName("forward:/index.html");
	    }

	    @Bean
	    public HttpMessageConverter<String> responseBodyConverter() { //统一编码
	        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
	        return converter;
	    }

	    //TODO 返回值如果为null不返回
	    @Bean
	    public ObjectMapper getObjectMapper() {
	        return new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
	    }

	    @Bean
	    public MappingJackson2HttpMessageConverter messageConverter() {
	        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	        converter.setObjectMapper(getObjectMapper());
	        return converter;
	    }

	    @Override
	    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	        converters.add(responseBodyConverter());
	        converters.add(messageConverter()); //解决处理中文问题后接口500问题
	    }

}
