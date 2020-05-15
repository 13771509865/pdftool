package com.neo.web.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.BindingResultUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ViewTokenBO;
import com.neo.service.file.DownLoadService;
import com.neo.service.httpclient.HttpAPIService;

import io.netty.handler.codec.http.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * @author xujun 文件下载接口
 */
@Api(value = "下载相关Controller", tags = {"下载相关Controller"})
@Controller
@RequestMapping(value = "/api/file")
public class DownLoadController {

	@Autowired
	private DownLoadService downLoadService;

	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private CloseableHttpClient httpClient;


	@ApiOperation(value = "下载文件")
	@PostMapping(value = "/download")
	@ResponseBody
	public String download(@Valid ViewTokenBO viewTokenBO,BindingResult bindingResult ,HttpServletResponse httpResponse,HttpServletRequest request) throws Exception{
		String erroMessage = BindingResultUtils.getMessage(bindingResult);
		if(StringUtils.isNotBlank(erroMessage)) {
			return erroMessage;
		}
		
		IResult<String> result = downLoadService.buildVToken(viewTokenBO);
		if(result.isSuccess()) {
			String vToken = result.getData();
			String fcsdownLoad =ptsProperty.getFcs_downLoad_url();
			String url = fcsdownLoad + "//" + vToken;

			URIBuilder uriBuilder = new URIBuilder(url);
			HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
			CloseableHttpResponse response = this.httpClient.execute(httpGet);
			if(!HttpUtils.isHttpSuccess(response.getStatusLine().getStatusCode())) {
				SysLogUtils.error("获取fcs文件内容失败，原因："+EntityUtils.toString(response.getEntity(), SysConstant.CHARSET));
				return null;
			}
			String filename = HttpUtils.getFileNameByDownload(response, url);
			boolean contentTypeSuccess = HttpUtils.setContentType(httpResponse, filename);
			if(contentTypeSuccess) {
				String codedfilename = HttpUtils.processFileName(request, filename);
				filename = URLEncoder.encode(filename,SysConstant.CHARSET);

				if(!StringUtils.isBlank(filename)) {
					httpResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", codedfilename));
					InputStream in = null;
					OutputStream out = null;
					try {
						in = response.getEntity().getContent();
						out = httpResponse.getOutputStream();
						int b;
						while ((b = in.read()) != -1) {
							out.write(b);
						}
					} catch (Exception e) {
						SysLogUtils.error(EnumResultCode.E_DOWNLOAD_FILE_FAIL.getInfo(),e);
						throw new Exception(EnumResultCode.E_DOWNLOAD_FILE_FAIL.getInfo()+":"+e.getMessage());
					}finally {
						try {
							in.close();
							out.close();
						} catch (IOException e) {
							SysLogUtils.error("IO流关闭异常,原因：",e);
							throw new Exception("IO流关闭异常");
						}
					}
				}
			}
			return null;
		}
		SysLogUtils.error(result.getMessage());
		return null;
	}


}

