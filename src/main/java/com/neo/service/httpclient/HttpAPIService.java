package com.neo.service.httpclient;


import com.alibaba.fastjson.JSONObject;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.FileHeaderEntity;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.StrUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.commons.util.UUIDHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service("httpApiService")
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

//    @Autowired
//    private RequestConfig requestConfig;

    /**
     * @param url    请求地址
     * @param params 请求参数map
     * @description 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     */
    public IResult<HttpResultEntity> doGet(String url, Map<String, Object> params, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            response = doGetProcess(url, params, headers);
            HttpResultEntity httpResultEntity = new HttpResultEntity(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), SysConstant.CHARSET));
            return DefaultResult.successResult(httpResultEntity);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("get请求失败,请求URL为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } finally {
            closeResource(response);
        }
    }

    /**
     * @param url 请求地址
     * @description 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     */
    public IResult<HttpResultEntity> doGet(String url) {
        return this.doGet(url, null, null);
    }

    public IResult<HttpResultEntity> doGet(String url, Map<String, Object> params) {
        return this.doGet(url, params, null);
    }

    /**
     * @param url    请求地址
     * @param params 请求参数map
     * @description 带参数的post请求
     */
    public IResult<HttpResultEntity> doPost(String url, Map<String, Object> params, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            response = doPostProcess(url, params, headers);
            HttpResultEntity httpResultEntity = new HttpResultEntity(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), SysConstant.CHARSET));
            return DefaultResult.successResult(httpResultEntity);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("post请求失败,请求URL为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } finally {
            closeResource(response);
        }
    }

    /**
     * @param url 请求参数
     * @description 不带参数post请求
     */
    public IResult<HttpResultEntity> doPost(String url) {
        return this.doPost(url, null, null);
    }

    public IResult<HttpResultEntity> doPost(String url, Map<String, Object> params) {
        return this.doPost(url, params, null);
    }

    /**
     * @param url     请求地址
     * @param jsonStr 请求参数jsonstr
     * @description 带参数的post请求, json方式
     */
    public IResult<HttpResultEntity> doPostByJson(String url, String jsonStr, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            response = doPostByJsonProcess(url, jsonStr, headers);
            HttpResultEntity httpResultEntity = new HttpResultEntity(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), SysConstant.CHARSET));
            return DefaultResult.successResult(httpResultEntity);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("postByJson请求失败,请求URL为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } finally {
            closeResource(response);
        }
    }

    public IResult<HttpResultEntity> doPostByJson(String url, String jsonStr) {
        return this.doPostByJson(url, jsonStr, null);
    }

    /**
     * @return 文件fileHash
     * @description 根据url获取文件唯一值
     * @author zhoufeng
     * @date 2019/4/10
     */
    public IResult<FileHeaderEntity> getFileHeaderBOByHead(String url, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
//            CloseableHttpResponse response = doHeadProcess(url, headers);
            response = doGetProcess(url, null, headers);
            Boolean httpSuccess = HttpUtils.isHttpSuccess(response.getStatusLine().getStatusCode());
            if (httpSuccess) {
                try {
                    String fileName = HttpUtils.getFileName(response, url);
                    Header contentLengthHeader = response.getFirstHeader("Content-Length");
                    Header lastModifiedHeader = response.getFirstHeader("Last-Modified");
                    Long contentLength = Long.valueOf(contentLengthHeader.toString().split(":")[1].trim());
                    String lastModified = lastModifiedHeader.toString().split("odified:")[1].trim();
                    if (contentLength != null && StringUtils.isNotEmpty(lastModified) && StringUtils.isNotEmpty(fileName)) {
                        FileHeaderEntity fileHeaderBO = new FileHeaderEntity(contentLength, lastModified, fileName, url);
                        return DefaultResult.successResult(fileHeaderBO);
                    }
                    return DefaultResult.failResult(EnumResultCode.E_FILEMD5_HEAD_FAIL.getInfo());
                } catch (Exception ex) {
                    return DefaultResult.failResult(EnumResultCode.E_FILEMD5_HEAD_FAIL.getInfo());
                }
            }
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("head方式获取文件头信息失败,文件Url为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } finally {
            closeResource(response);
        }
    }

    public IResult<FileHeaderEntity> getFileHeaderBOByHead(String url) {
        return getFileHeaderBOByHead(url, new HashMap<>());
    }

    /**
     * @description 下载文件
     * @author zhoufeng
     * @date 2019/4/10
     */
    public IResult<String> download(String url, Map<String, Object> params, Map<String, Object> headers, String filePath, String fileName) {
        CloseableHttpResponse response = null;
        try {
            //模拟浏览器,不然可能下载失败
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            response = doGetProcess(url, params, headers);
            Boolean httpSuccess = HttpUtils.isHttpSuccess(response.getStatusLine().getStatusCode());
            if (httpSuccess) {
                InputStream inputStream = response.getEntity().getContent();
                if (StringUtils.isEmpty(fileName)) {
                    fileName = HttpUtils.getFileNameByDownload(response, url);
                }
                File destFile = new File(filePath, fileName);
                FileUtils.copyInputStreamToFile(inputStream, destFile);
                return DefaultResult.successResult(destFile.getAbsolutePath());
            }
            return DefaultResult.failResult(EnumResultCode.E_DOWNLOAD_FILE_FAIL.getInfo(), EnumResultCode.E_DOWNLOAD_FILE_FAIL.getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("下载文件失败,文件Url为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_DOWNLOAD_FILE_FAIL.getInfo(), EnumResultCode.E_DOWNLOAD_FILE_FAIL.getValue().toString());
        } finally {
            closeResource(response);
        }
    }

    private void addHttpHeader(HttpRequestBase http, Map<String, Object> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                if (entry.getValue() != null) {
                    http.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
        }
    }

    private CloseableHttpResponse doPostByJsonProcess(String url, String jsonStr, Map<String, Object> headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (!StringUtils.isEmpty(jsonStr)) {
            StringEntity stringEntity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        addHttpHeader(httpPost, headers);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return response;
    }

    private CloseableHttpResponse doHeadProcess(String url, Map<String, Object> headers) throws Exception {
        HttpHead httpHead = new HttpHead(url);
        // 装载配置信息
//        httpGet.setConfig(config);
        addHttpHeader(httpHead, headers);
        CloseableHttpResponse response = this.httpClient.execute(httpHead);
        return response;
    }

    private CloseableHttpResponse doGetProcess(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
        // 装载配置信息
//        httpGet.setConfig(config);
        addHttpHeader(httpGet, headers);
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        return response;
    }

    private CloseableHttpResponse doPostProcess(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    Object v = entry.getValue();
                    if (v instanceof ArrayList) {
                        try {
                            Iterator<Object> it = ((ArrayList) v).iterator();
                            while (it.hasNext()) {
                                JSONObject jsonObj = (JSONObject) it.next();
                                list.add(new BasicNameValuePair(entry.getKey(), jsonObj.toString()));
                            }
                        } catch (Exception e) {
                            Iterator<Object> it = ((ArrayList) v).iterator();
                            while (it.hasNext()) {
                                Object jsonObj = (Object) it.next();
                                list.add(new BasicNameValuePair(entry.getKey(), jsonObj.toString()));
                            }
                        }
                    } else {
                        list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                }
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
        }
        addHttpHeader(httpPost, headers);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return response;
    }

    private void closeResource(CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
        }
    }


    /**
     * 服务器之间文件传输
     *
     * @param file
     * @param url
     * @return
     */
    public IResult<HttpResultEntity> uploadResouse(MultipartFile file, String url) {
        CloseableHttpResponse response = null;
        try {
            response = uploadProcess(file, null, url, null, null);
            HttpResultEntity httpResultEntity = new HttpResultEntity(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), SysConstant.CHARSET));
            return DefaultResult.successResult(httpResultEntity);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("upload--fcs请求失败,请求URL为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } finally {
            closeResource(response);
        }
    }

    private CloseableHttpResponse uploadProcess(MultipartFile muFile, File file, String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        //不自定义分隔符,struts2有上传问题
        builder.setBoundary("--------------------------"+ UUIDHelper.generateUUID());
        if (muFile != null) {
           builder.addBinaryBody("file", muFile.getBytes(), ContentType.MULTIPART_FORM_DATA, StrUtils.replaceSpace(muFile.getOriginalFilename()));
        }
        if (file != null) {
            builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName());
        }
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addTextBody(key, params.get(key).toString(), ContentType.create("text/plain", StandardCharsets.UTF_8));
            }
        }
        httpPost.setEntity(builder.build());
        addHttpHeader(httpPost, headers);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return response;
    }

    /**
     * 跨域服务器之间文件的传送
     *
     * @param file
     * @param url
     * @return
     * @author xujun
     * @date 2019-07-19
     */
    public IResult<HttpResultEntity> uploadResouse(File file, String url, Map<String, Object> params, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            response = uploadProcess(null, file, url, params, headers);
            HttpResultEntity httpResultEntity = new HttpResultEntity(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), SysConstant.CHARSET));
            return DefaultResult.successResult(httpResultEntity);
        } catch (Exception e) {
            e.printStackTrace();
            SysLogUtils.info("upload--fcs请求失败,请求URL为:" + url);
            return DefaultResult.failResult(EnumResultCode.E_HTTP_SEND_FAIL.getInfo());
        } finally {
            closeResource(response);
        }

    }


    public static Boolean isHttpSuccess(Integer code) {
        if (code != null) {
            if (code >= 200 && code < 300) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        HttpAPIService h = new HttpAPIService();
        String url = "http://172.18.21.30:8080/fcscloud/view/download/s8mSvTjKia8LKgyJoI_cTCz0TwesMEEwjn6OgahAEWzSByUclpymeFWJ3B5U_mBQuhbASnaZRpbGSvkVCucUI_0v1bi9FLQbky9GsETZTWzYsxaHf6MdmagNd1IfYJmTDq4SpmWk4QxZ872PFmJsD2Tu6MeiT9oUJ0bPQh5WXKjFo4489bi3cXtRLE69ySrj-6EkWyuKEwnW5dWjQ_tUeiComggFvBCKVQ4kUSAXvISYFbBRKiHhxpfQ0DwZeuBepcOudGl7tgF58mcPGB5-d4Wc01FpYEf7FZyDc6_7Qzw";
        IResult<HttpResultEntity> r = h.doGet(url);
        System.out.println(r.getData().toString());
    }


}