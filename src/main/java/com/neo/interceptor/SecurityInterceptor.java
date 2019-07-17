package com.neo.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.permission.EnumAdmin;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.UserBO;
import com.neo.model.po.UserPO;



/**
 * 
 * @author Sumnear
 *
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		IResult<Object> checkSecurityResult = checkSecurity(request);
		if (!checkSecurityResult.isSuccess()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.fail(checkSecurityResult.getMessage()));
			out.flush();
			out.close();
			return false;
		}
		return true;
	}

	// 检查session中的对象
	private IResult<Object> checkSecurity(HttpServletRequest request) {
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		//获取session中的对象
		Object userObj = session.getAttribute(SysConstant.SESSION_USER);
		if (userObj == null || !(userObj instanceof UserPO)) {
			return DefaultResult.failResult("无用户信息请重新登陆");
		}
		if(uri.contains("/manager/")) {
			UserPO  user = (UserPO)userObj;
			if(SysConstant.SUPER_MAN.equals(user.getUsername())||EnumAdmin.isManager(user.getRoleId())) {
				return DefaultResult.successResult();
			}else {
				return DefaultResult.failResult("用户无权限");
			}
		}else {
			return DefaultResult.successResult();
		}
		

//		boolean isAllow = isAllow(uri,sysUserDO);
//		return isAllow?DefaultResult.successResult(): DefaultResult.failResult("用户无权限");
	}
	//检查用户是否拥有权限
//    private boolean isAllow(String uri,SysUserDO sysUserDO){
//    	if(EnumStatus.DISABLE.getValue().equals(sysUserDO.getRoleStatus())){
//			return false;
//		}
//    	//所有需要拦截的权限
//    	List<ActionListVO> allList = actionList_root.getChilder();
//    	Integer[] privilegeList = sysUserDO.getPrivilegeList();
//    	for (ActionListVO actionListVO : allList) {
//    		//如果该权限需要被拦截
//			if(uri.indexOf(actionListVO.getActionUrl())>-1){
//				//检查用户权限中是否包含该权限
//				for (Integer privilege : privilegeList) {
//					if(actionListVO.getActionId().equals(privilege)){
//						return true;
//					}
//				}
//				return false;
//			}
//		}
//    	return true;
//    }
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

}
