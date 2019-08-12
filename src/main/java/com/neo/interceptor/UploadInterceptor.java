package com.neo.interceptor;

import java.io.IOException;
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
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.constants.ConstantAdmin;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.accessTimes.AccessTimesService;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.cache.impl.RedisCacheManager;

/**
 * 
 * 对用户上传权限做拦截
 * 游客上传10M
 * 会员上传30M
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
		Object uploadResult = request.getAttribute(SysConstant.UPLOAD_RESULT);
		if(uploadResult != null && uploadResult instanceof Integer) {
			if (EnumResultCode.E_SUCCES.getValue() == uploadResult) {
				redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT, RedisConsts.SUCCESS);
			}else {
				redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT,RedisConsts.FAIL);
			}
		}else {
			redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT,RedisConsts.FAIL);
		}

	}


	//游客仅可上传10M,登录用户可上传30M
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);

		//参数默认为游客，文件大小为5M
		long maxSize = VUploadSize;
		EnumResultCode resultCode = EnumResultCode.E_VISITOR_UPLOAD_ERROR;

		//登录用户，30M
		if(StringUtils.isNotBlank(userInfo)) {
			maxSize = MUploadSize;
			resultCode = EnumResultCode.E_USER_UPLOAD_ERROR;
		}
		IResult<String> result = checkFile(request,maxSize);
		if(!result.isSuccess()) {
			redisCacheManager.pushZSet(RedisConsts.UPLOAD_CONNT,RedisConsts.FAIL);//被拦截也统计一下次数
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
