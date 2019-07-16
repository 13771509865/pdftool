package com.neo.web;

import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.JsonResultUtils;
import com.neo.config.SysConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * 上传的控制器
 *
 * @authore sumnear
 * @create 2018-12-10 20:34
 */

@Controller
public class UploadController
{

    @Autowired
    private SysConfig config;


    @RequestMapping(value = "/defaultUpload")
    @ResponseBody
    //@RequestParam("file") CommonsMultipartFile file
    public Map<String, Object> fileUpload(HttpServletRequest request) throws  IOException
    {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            List<String> result = new ArrayList<>();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null )
                {
                    String relativePath = UUID.randomUUID()+File.separator+file.getOriginalFilename();
                    String path=config.getInputDir() +File.separator+relativePath;
                    File targetFile =  new File(path);
                    if(!targetFile.exists()) {
                    	targetFile.getParentFile().mkdirs();
                    	targetFile.createNewFile();
                    }
                    //上传
                    file.transferTo(targetFile);
                    result.add(relativePath);
                }
            }
            request.setAttribute(SysConstant.UPLOAD_RESULT, result.size()); //用于拦截器统计信息
            return JsonResultUtils.successMapResult(result);
        }else{
            return JsonResultUtils.failMapResult("上传文件有误！");
        }
//        String path=config.getInputDir() +file.getOriginalFilename();
//        File newFile=new File(path);
//        try {
//            file.transferTo(newFile);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return JsonResultUtils.successMapResult(path);
    }
}
