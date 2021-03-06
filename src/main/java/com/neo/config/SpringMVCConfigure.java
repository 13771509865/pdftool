package com.neo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.neo.json.JsonReturnHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
@EnableWebMvc
public class SpringMVCConfigure implements WebMvcConfigurer{
	
//	@Autowired
//	private UploadInterceptor uploadInterceptor;
//
//	@Autowired
//	private ConvertInterceptor convertInterceptor;
//
//	@Autowired
//	private UaaAuthInterceptor uaaAuthInterceptor;
//
//	@Autowired
//	private FeedBackInterceptor feedBackInterceptor;
	
	
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
//	        registry.addInterceptor(uaaAuthInterceptor)
//	        .addPathPatterns("/composite/**")
//	        .addPathPatterns("/file/**")
//	        .addPathPatterns("/statistics/**")
//	        .addPathPatterns("/uaa/**")
//	        .addPathPatterns("/feedback")
//	        .excludePathPatterns("/uaa/setCookie")
//	        .excludePathPatterns("/statistics/modules")
//			.excludePathPatterns("/statistics/show")
//			.excludePathPatterns("/statistics/version");
//	        registry.addInterceptor(uploadInterceptor).addPathPatterns("/file/defaultUpload","/file/uploadYc");
//	        registry.addInterceptor(convertInterceptor).addPathPatterns("/composite/**");
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

	    
		/**
		 * Long类型返回前端处理
		 * @return
		 */
		@Bean
	    public ObjectMapper getObjectMapper() {
			SimpleModule simpleModule = new SimpleModule();
	        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
	        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
	        return new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL).registerModule(simpleModule);
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


		@Bean
		public JsonReturnHandler jsonReturnHandler(){
			return new JsonReturnHandler();//初始化json过滤器
		}

		@Override
		public void addReturnValueHandlers( List<HandlerMethodReturnValueHandler> returnValueHandlers) {
			returnValueHandlers.add(jsonReturnHandler());
		}



}
