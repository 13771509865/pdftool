package com.neo.controller.user;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.neo.commons.cons.ResultCode;
import com.neo.commons.util.JsonResultUtils;
import com.neo.config.SysConfig;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FileInfoBO;
import com.neo.service.file.FileService;

/**
 * 文件删除接口
 * @author zhouf
 * @create 2018-12-13 20:42
 */
@Controller
@RequestMapping(value = "/user")
public class DeleteFileController {
	@Autowired
	private SysConfig config;
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/deleteFile")
    @ResponseBody
    public Map<String, Object> deleteFile(@RequestParam(value = "fileHash", required = true) String fileHash,HttpSession httpSession)  {
		Object obj = httpSession.getAttribute(fileHash);
		FileInfoBO fileInfoBO = (FileInfoBO)obj;
		String srcRelativePath = fileInfoBO.getStoragePath();
		if(StringUtils.isEmpty(srcRelativePath)){
			return JsonResultUtils.failMapResultByCode(ResultCode.E_INVALID_PARAM);
		}
		String srcPath = config.getInputDir() + File.separator + srcRelativePath;
		fileService.deleteSrcFile(srcPath);
		return JsonResultUtils.successMapResult();
    }
}
