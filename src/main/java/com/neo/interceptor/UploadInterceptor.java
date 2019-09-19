package com.neo.interceptor;

import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.file.UploadService;

/**
 * 
 * 对用户上传权限做拦截
 * @author xujun
 * 2019-07-11
 *
 */
@Component
public class UploadInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private ConfigProperty config;
	
	@Autowired
	private UploadService uploadService;

	private static int MUploadSize ;

	private static int VUploadSize ;


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}


	/**
	 * 统计上传失败和成功的次数
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
//		Object uploadResult = request.getAttribute(SysConstant.UPLOAD_RESULT);
//		if(uploadResult != null && uploadResult instanceof Integer) {
//			if (EnumResultCode.E_SUCCES.getValue() == uploadResult) {
//				redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT, RedisConsts.SUCCESS);
//			}else {
//				redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT,RedisConsts.FAIL);
//			}
//		}else {
//			redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT,RedisConsts.FAIL);
//		}
		
		
	

	}


	//游客仅可上传2M,登录用户不限制
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);

		//参数默认为游客，文件大小为2M
		long maxSize = VUploadSize;
		EnumResultCode resultCode = EnumResultCode.E_VISITOR_UPLOAD_ERROR;

		//登录用户,不做限制
		if(StringUtils.isNotBlank(userInfo)) {
			maxSize = MUploadSize;
			resultCode = EnumResultCode.E_USER_UPLOAD_ERROR;
		}
		IResult<String> result = checkFile(request,maxSize);
		if(!result.isSuccess()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(resultCode));
			out.flush();
			out.close();
			return false;
		}
		return true;
	}

	//检查上传的文件是否超过maxSize
	private IResult<String> checkFile(HttpServletRequest request, long maxSize){
		if(request!=null && ServletFileUpload.isMultipartContent(request)) {
			ServletRequestContext ctx = new ServletRequestContext(request);
			long requestSize = ctx.contentLength();
			if (requestSize > maxSize) {
				return DefaultResult.failResult();
			}
		}
		return DefaultResult.successResult();
	}


	//初始化文件的大小
	@PostConstruct
	private void initCache() {
		MUploadSize = config.getMUploadSize()*1024*1024;
		VUploadSize = config.getVUploadSize()*1024*1024;
	}




}
