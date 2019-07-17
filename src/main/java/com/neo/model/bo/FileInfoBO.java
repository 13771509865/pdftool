package com.neo.model.bo;

/**
 * ${DESCRIPTION}
 *
 * @authore sumnear
 * @create 2018-12-13 19:40
 */
/**
 * Description:
 * Date: 2015年12月14日
 * Author: zhao_yuanchao
 */

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neo.commons.cons.constants.PathConsts;


public class FileInfoBO
{
    private static final Logger log = LoggerFactory.getLogger(FileInfoBO.class);

    /* 该文件是否是需要转换的文件, 主要是提交FileInfo信息到巴别鸟web服务器时使用. */
    private boolean needConvert = false;

    /* 用户上传文件时对应的session id */
    private String sid = "";

    /* 需要转换或者生成缩略图的本地文件路径 */
    private String fileAbsolutePath = "";

    /* 代表在云存储上的路径或者本地存储的存盘路径 */
    private String storagePath = "";

    /* 直接的上传者 */
    private long committer = 0;

    /* 拥有者 */
    private long owner = 0;

    private long fileId = 0;
    private long folderId = 0;
    private String fileName = "";
    private int version = 0;
    private long fileSize = 0;

    /* 数据库fileRef表中的id列, 主要用户转换后, 要修改该表中pageCount列 */
    private long fileRefId = 0;

    /* 数据库fileRef表中的pageCount */
    private int pageCount = 0;

    /* pdf文件的每一页的大小 */
    private List<Float> pageSize = new ArrayList<Float>();

    /* 需要推送到的消息队列的topic和tag */
    private String topic = "";
    private String tag = "";

    /* 文件所属的企业Id, 默认是0, 代表不是属于任何企业. */
    private long enterpriseId = 0;

    /*
     * 文件是否属于专业版(文件的只能是个人版、专业版、企业版中的一个)
     * 文档转换服务器不用关心此变量, 会在Web服务器中设置
     */
    private int editionId = 0;

    /*
     * 引用计数, 当referenceCount小于等于0时删除fileAbsolutePath上的文件.
     * 主要是针对既要生成缩略图, 又要上传到云存储的中间pdf文件.
     * 当采用本地存储时, 不需要删除生成的pdf, pdf会保存到相应的目录下.
     */
    private Integer referenceCount = 0;

    /* 是否是点击转换的文档. */
    private boolean isClickConvert = false;

    /* 在等待队列中的时间, 调度服务器会使用这个时间. */
    private long waitTime;

    /* 转换的优先级, 暂时不使用. */
    private int priority;

    /* 文件转换的结果 */
    private int code = 0;

    /* 水印信息 */
    private String watermarkInfo;

    /* 文件的md5哈希值 */
    private String fileHash = "";

    /* 文件是否是秒传的 */
    private boolean isSecUpload = false;

    /* 对于转换为pdf的文件, 此处存放打开密码. */
    private String pdfPassword = "";

    private String uploadToken = "";// 批量上传使用

    private String wordHash = "";// pdf转出的word的hash

    private String pdfHash = "";// 转word的pdf的hash

    /* 代表在云存储上的路径或者本地存储的存盘路径 */
    private String wordStoragePath = "";

    /* 该文件是否是需要转换的文件, 转换的形式 */
    private String convertType = "";

    public FileInfoBO() {

    }

    public FileInfoBO(String fileAbsolutePath, String fileName) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.fileName = fileName;
    }

    public FileInfoBO(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

//	public FileInfo(String fileAbsolutePath, long owner, long committer, long fileId, int version) {
//		this.fileAbsolutePath = fileAbsolutePath;
//		this.owner = owner;
//		this.committer = committer;
//		this.fileId = fileId;
//		this.version = version;
//	}

    public void methodInvoke(String fieldName, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, this.getClass());
            Method setter = pd.getWriteMethod();//获得set方法
            setter.invoke(this, value);
        } catch (IntrospectionException e) {
            log.error("FileInfo方法调用, IntrospectionException:" + e.getMessage());
        } catch (IllegalAccessException e) {
            log.error("FileInfo方法调用, IllegalAccessException:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("FileInfo方法调用, IllegalArgumentException:" + e.getMessage());
        } catch (InvocationTargetException e) {
            log.error("FileInfo方法调用, InvocationTargetException:" + e.getMessage());
        }

        //获得get方法
        //Method get = pd.getReadMethod();
        //Object getValue = get.invoke(this, new Object[]{});
    }

    public String serializeData() {
        return serializeData(this);
    }

    public static String serializeData(FileInfoBO info) {
        return JSON.toJSONString(info);
    }

//    public static FileInfoBO deserializeData(String str) {
//        return JSON.parseObject(str);
//    }
    
    public static <T> T json2obj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    
    public static void main(String[] args) {
    	String str = "\"{\\\"clickConvert\\\":false,\\\"code\\\":0,\\\"committer\\\":0,\\\"convertType\\\":\\\"\\\",\\\"editionId\\\":0,\\\"enterpriseId\\\":0,\\\"fileAbsolutePath\\\":\\\"\\\",\\\"fileHash\\\":\\\"37272201681bf0e48ea7b4bb3d9d5a7302E7F83627666BF0DA47AC47261EE510\\\",\\\"fileId\\\":0,\\\"fileName\\\":\\\"无锡市房屋征收评估技术规范.pdf\\\",\\\"fileRefId\\\":0,\\\"fileSize\\\":0,\\\"folderId\\\":0,\\\"needConvert\\\":false,\\\"owner\\\":0,\\\"pageCount\\\":0,\\\"pageSize\\\":[],\\\"pdfHash\\\":\\\"\\\",\\\"pdfPassword\\\":\\\"\\\",\\\"priority\\\":0,\\\"secUpload\\\":false,\\\"sid\\\":\\\"\\\",\\\"storagePath\\\":\\\"2cd96314-5c81-475f-b92a-e4dc02bd39ef\\\\\\\\无锡市房屋征收评估技术规范.pdf\\\",\\\"tag\\\":\\\"\\\",\\\"topic\\\":\\\"\\\",\\\"uploadToken\\\":\\\"\\\",\\\"version\\\":0,\\\"waitTime\\\":0,\\\"wordHash\\\":\\\"\\\",\\\"wordStoragePath\\\":\\\"2019\\\\\\\\07\\\\\\\\15\\\\\\\\37272201681bf0e48ea7b4bb3d9d5a7302E7F83627666BF0DA47AC47261EE510\\\\\\\\无锡市房屋征收评估技术规范.docx\\\"}";
    	FileInfoBO bo =json2obj(str,FileInfoBO.class);
    	System.out.println(bo);
	}
    
    public static FileInfoBO deserializeDataWithMethodInvoke(String str) {
        JSONObject obj = JSONObject.parseObject(str);
        FileInfoBO info = new FileInfoBO();
        for (Object iter : obj.keySet()) {
            String key = iter.toString();
            info.methodInvoke(key, obj.get(key));
        }
        return info;
    }

    public FileInfoBO addReferenceCount() {
        synchronized (this.referenceCount) {
            ++this.referenceCount;
        }
        return this;
    }

    public void decreaseReferenceCount() {
        synchronized (this.referenceCount) {
            --this.referenceCount;
            if (this.referenceCount <= 0) {
                File file = new File(this.fileAbsolutePath);
                if (file.exists()) {
                    try {
                        if (this.referenceCount == 0) {
                            FileUtils.forceDelete(file);
                        } else {
                            FileUtils.forceDeleteOnExit(file);
                        }
                    } catch (Exception e) {
                        log.error("删除文件失败:" + e.getMessage());
                    }
                }
            }
        }
    }



    /*
     * 生成用户上传文件的父目录
     */
    public String makeStorageFolderPath() {
        /*
         * 注意一定不要使用文件分隔符, windows下的分隔符和Linux下的不一样, 一律采用'/'为路径分隔符.
         * d:/mola/BabelStorage/document/20160612/b_1536445015202426/0/0.jpg
         * /home/mola/BabelStorage/document/20160612/b_1536445015202426/0/0.jpg
         * document/b_1536445015202426/0/0.jpg
         * 29305/document/b_3445851818443392/0/0.jpg
         */
        int pos = this.storagePath.lastIndexOf(PathConsts.Cloud_Separator);
        return this.storagePath.substring(0, pos);
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public long getCommitter() {
        return committer;
    }

    public void setCommitter(long committer) {
        this.committer = committer;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public long getFileRefId() {
        return fileRefId;
    }

    public void setFileRefId(long fileRefId) {
        this.fileRefId = fileRefId;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Float> getPageSize() {
        return pageSize;
    }

    public void setPageSize(List<Float> pageSize) {
        this.pageSize = pageSize;
        this.pageCount = (short)(pageSize.size()/2);
    }

    public boolean isNeedConvert() {
        return needConvert;
    }

    public void setNeedConvert(boolean needConvert) {
        this.needConvert = needConvert;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public int getEditionId() {
        return editionId;
    }

    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    public boolean isClickConvert() {
        return isClickConvert;
    }

    public void setClickConvert(boolean isClickConvert) {
        this.isClickConvert = isClickConvert;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String fileExtension() {
        return FilenameUtils.getExtension(this.fileName).toLowerCase();
    }

    public String getWatermarkInfo() {
        return watermarkInfo;
    }

    public void setWatermarkInfo(String watermarkInfo) {
        this.watermarkInfo = watermarkInfo;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public boolean isSecUpload() {
        return isSecUpload;
    }

    public void setSecUpload(boolean isSecUpload) {
        this.isSecUpload = isSecUpload;
    }

    public String getPdfPassword() {
        return pdfPassword;
    }

    public void setPdfPassword(String pdfPassword) {
        this.pdfPassword = pdfPassword;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    public String getWordHash() {
        return wordHash;
    }

    public void setWordHash(String wordHash) {
        this.wordHash = wordHash;
    }

    public String getPdfHash() {
        return pdfHash;
    }

    public void setPdfHash(String pdfHash) {
        this.pdfHash = pdfHash;
    }

    public String getWordStoragePath() {
        return wordStoragePath;
    }

    public void setWordStoragePath(String wordStoragePath) {
        this.wordStoragePath = wordStoragePath;
    }

    public String getConvertType() {
        return convertType;
    }

    public void setConvertType(String convertType) {
        this.convertType = convertType;
    }
}