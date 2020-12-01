package com.neo.interceptor;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.constants.SessionConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.entity.FileHeaderEntity;
import com.neo.commons.cons.entity.ModuleEntity;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.auth.IAuthService;
import com.neo.service.file.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对用户上传权限做拦截
 *
 * @author xujun
 * 2019-07-11
 */
@Component
public class UploadInterceptor implements HandlerInterceptor {


    @Autowired
    private IAuthService iAuthService;

    @Autowired
    private UploadService uploadService;


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception arg3) throws Exception {
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
            throws Exception {
    }


    /**
     * 检查用户的上传文件大小权限
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String module = request.getParameter(PtsConsts.MODULE);
        String nonceParm = request.getHeader(PtsConsts.HEADER_NONCE);

        //检查加密内容
        IResult<ModuleEntity> checkModuleResult = uploadService.checkModule(module,nonceParm);
        if(!checkModuleResult.isSuccess()){
            HttpUtils.sendResponse(request, response, JsonResultUtils.buildFailJsonResultByResultCode(EnumResultCode.E_UPLOAD_FILE));
            return false;
        }

        ServletRequestContext ctx = new ServletRequestContext(request);

        Long uploadSize = ctx.contentLength();


        //判断是普通上传还是优云上传
        if (StringUtils.isNotBlank(request.getParameter(PtsConsts.YCFILEID))) {
            String cookie = request.getHeader(UaaConsts.COOKIE);
            String accessToken = request.getHeader(UaaConsts.HEADER_ACCESS_TOKEN);
            String refreshToken = request.getHeader(UaaConsts.HEADER_REFRESH_TOKEN);

            //拿优云的文件链接
            IResult<FileHeaderEntity> fileHeaderEntity = uploadService.getFileHeaderEntity(request.getParameter(PtsConsts.YCFILEID), cookie, accessToken, refreshToken);
            if (!fileHeaderEntity.isSuccess()) {
                HttpUtils.sendResponse(request, response, JsonResultUtils.fail((fileHeaderEntity.getMessage())));
                return false;
            }
            uploadSize = fileHeaderEntity.getData().getContentLength();
            request.setAttribute(SessionConstant.FILE_HEADER_ENTITY, fileHeaderEntity.getData());
        }

        //验证上传文件大小权限
        IResult<EnumResultCode> result = iAuthService.checkUploadSize(HttpUtils.getUaaToken(request), uploadSize, checkModuleResult.getData().getModule());
        if (!result.isSuccess()) {
            HttpUtils.sendResponse(request, response, JsonResultUtils.buildFailJsonResultByResultCode(result.getData()));
            return false;
        }
        return true;
    }


}
