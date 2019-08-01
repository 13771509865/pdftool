package com.neo.model.bo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * 转码参数
 *
 * @authore zhouf
 * @create 2018-12-11 19:31
 * 加参数的时候注意GetConvertMd5Utils类
 */
@ApiModel(value = "convertParameterBO对象",description = "文档转换参数对象")
public class ConvertParameterBO implements Serializable {
    //通用参数
    @ApiModelProperty(value = "转换超时时间",example="30")
    private Long convertTimeOut;
    @ApiModelProperty(value = "转换超时时间",hidden = true)
    private String srcFileName;
    @ApiModelProperty(value = "真实的生成的文件名",hidden = true)
    private String destFileName;
    @ApiModelProperty(value = "源文件全路径",hidden = true)
    private String srcPath;
    @ApiModelProperty(value = "目标文件全路径",hidden = true)
    private String destPath;
    @ApiModelProperty(value = "转换类型",required = true,example="0")
    private Integer convertType;
    @ApiModelProperty(value = "源文件相对路径",required = true)
    private String srcRelativePath;
    @ApiModelProperty(value = "目标文件相对路径",hidden = true)
    private String destRelativePath;
    @ApiModelProperty(value = "回调地址")
    private String callBack;
    @ApiModelProperty(value = "文件md5",hidden = true)
    private String fileHash;
    @ApiModelProperty(value = "源文件大小",hidden = true,example="0")
    private Long srcFileSize;
    @ApiModelProperty(value = "调用方式(同步,异步,MQ)",example="0")
    private Integer callType;
    @ApiModelProperty(value = "异步preview用的,用于redis中的",hidden = true)
    private String convertId;
    @ApiModelProperty(value = "是否强制转换1是0否",example = "0")
    private Integer noCache;

    private String fcsCustomData; //fcs自定义数据,直接加在返回结果中

    @ApiModelProperty(value = "是否删除源文件1是0否",example="0")
    private Integer isDelSrc;
    @ApiModelProperty(value = "原文档下载地址",hidden = true)
    private String inputUrl;
    @ApiModelProperty(value = "自定义output相对路径")
    private String appendPath;
    @ApiModelProperty(value = "自定义生成的文件名")
    private String destinationName;
    //DCC参数
    @ApiModelProperty(value = "转换后的标题名字")
    private String htmlName;
    @ApiModelProperty(value = "转换的编码")
    private String encoding;
    @ApiModelProperty(value = "转换后html标签名字")
    private String htmlTitle;
    @ApiModelProperty(value = "是否防复制1是0否",example="0")
    private Integer isCopy;
    @ApiModelProperty(value = "是否显示黑条1是0否",example="1")
    private Integer isShowTitle;
    @ApiModelProperty(value = "转换页数",example="1")
    private Integer[] page;
    @ApiModelProperty(value = "缩放比例",example="1")
    private Float zoom;
    @ApiModelProperty(value = "文档合并")
    private String[] mergeInput;
    @ApiModelProperty(value = "添加书签内容")
    private String[] bookMark;
    @ApiModelProperty(value = "水印内容")
    private String wmContent;
    @ApiModelProperty(value = "水印字体大小",example="100")
    private Integer wmSize;
    @ApiModelProperty(value = "水印颜色",example="5")
    private Integer wmColor;
    @ApiModelProperty(value = "水印字体")
    private String wmFont;
    @ApiModelProperty(value = "静态水印间距",example="150")
    private Integer wmSpace;
    @ApiModelProperty(value = "静态水印透明度",example="1")
    private Float wmTransparency;
    @ApiModelProperty(value = "静态水印内容旋转角度",example="-50")
    private Integer wmRotate;
    @ApiModelProperty(value = "静态文件内容是否在文字上方还是下方",example="0")
    private Integer wmSuspend;
    @ApiModelProperty(value = "静态水印的位置(ofd的)",example="5")
    private Integer position;
    @ApiModelProperty(value = "//是否是平铺效果，默认是的，默认为1",example="1")
    private Integer wmTileEffect;
    @ApiModelProperty(value = "颜色是否使用rgb方式，默认是0,不选，1是使用",example="0")
    private Integer wmRgb;
    @ApiModelProperty(value = "rgb红 绿 蓝 范围是0~255",example="128")
    private Integer wmRgbRed;
    @ApiModelProperty(value = "rgb红 绿 蓝 范围是0~255",example="128")
    private Integer wmRgbBlue;
    @ApiModelProperty(value = "rgb红 绿 蓝 范围是0~255",example="128")
    private Integer wmRgbGreen;
    @ApiModelProperty(value = "图片水印路径")
    private String wmPicPath;
    @ApiModelProperty(value = "是否一次性展示1是0否",example="0")
    private Integer isShowAll;
    @ApiModelProperty(value = "加密文档密码")
    private String password;
    @ApiModelProperty(value = "是否显示文档目录",example="1")
    private Integer isShowList;
    @ApiModelProperty(value = "是否显示页脚",example="1")
    private Integer showFooter;
    @ApiModelProperty(value = "是否显示批注",example="1")
    private Integer acceptTracks;
    @ApiModelProperty(value = "是否隐藏空白",example="0")
    private Integer isHideBlank;
    @ApiModelProperty(value = "是否竖排",example="0")
    private Integer isVertical;
    @ApiModelProperty(value = "是否显示打印",example="0")
    private Integer isPrint;
    @ApiModelProperty(value = "是否显示页码",example="1")
    private Integer isShowNum;
    @ApiModelProperty(value = "生成文件相对路径")
    private String htmlPath;
    @ApiModelProperty(value = "动态水印")
    private String dynamicMark;
    @ApiModelProperty(value = "动态水印x轴内容之间距离")
    private String dmXextra;
    @ApiModelProperty(value = "动态水印y轴内容之间距离")
    private String dmYextra;
    @ApiModelProperty(value = "动态水印的文字大小")
    private String dmFontSize;
    @ApiModelProperty(value = "动态水印的透明度(范围是0到1)",example="0.5")
    private Float dmAlpha;
    @ApiModelProperty(value = "动态水印旋转角度")
    private String dmAngle;
    @ApiModelProperty(value = "动态水印字体")
    private String dmFont;
    @ApiModelProperty(value = "动态水印颜色,rgb方式，如rgb(0,0,0),#000000")
    private String dmColor;
    @ApiModelProperty(value = "是否提供原文档下载",example="0")
    private Integer isDownload;
    @ApiModelProperty(value = "是否防下载",example="0")
    private Integer antiDownload;
    @ApiModelProperty(value = "是否有全屏按钮",example="0")
    private Integer isFullScreen;
    @ApiModelProperty(value = "pdf标题")
    private String pdfTitle;
    @ApiModelProperty(value = "pdf作者")
    private String pdfAuthor;
    @ApiModelProperty(value = "pdf主题")
    private String pdfSubject;
    @ApiModelProperty(value = "pdf关键字")
    private String pdfKeyword;
    @ApiModelProperty(value = "自定义模板相对路径")
    private String jsPath;
    @ApiModelProperty(value = "源文件需要被替换的文字")
    private String[] sourceReplace;
    @ApiModelProperty(value = "源文件要替换成的文字")
    private String[] targetReplace;
    @ApiModelProperty(value = "dcc是否异步",example="0")
    private Integer isDccAsync;
    @ApiModelProperty(value = "是否显示边框",example="0")
    private Integer isShowGridline;
    @ApiModelProperty(value = "是否显示全单元格",example="0")
    private Integer isShowColWidth;
    @ApiModelProperty(value = "word给word加入文字或图片水印开关",example="0")
    private Integer wmWord;
    @ApiModelProperty(value = "控制图片水印的x轴位置",example="0")
    private Integer wmX;
    @ApiModelProperty(value = "控制图片水印的Y轴位置",example="0")
    private Integer wmY;
    @ApiModelProperty(value = "获取接收的base64图片",hidden = true)
    private String wmImage;
    @ApiModelProperty(value = "图片水印是否被拉伸和页面一直大小，默认不拉升，为0",example="0")
    private Integer wmPull;
    @ApiModelProperty(value = "图片转图片,生成图片的后缀名，默认是jpg")
    private String imageType;
    @ApiModelProperty(value = "word转word之后，设置是否可编辑，默认可以，0可以编辑，1不可以编辑",example="0")
    private Integer wordEdit;
    @ApiModelProperty(value = "是否进入签批模式,1是0否",example="0")
    private Integer isSignature;
    @ApiModelProperty(value = "签批调用的接口",hidden = true)
    private String signatureUrl;
    @ApiModelProperty(value = "签批生成的图片路径，需要开启图片水印地址参数",hidden = true)
    private String[] signatureImgPath;
    @ApiModelProperty(value = "生成的的html的文件夹路径",hidden = true)
	private String signatureFolder;
    @ApiModelProperty(value = "需要更新的svg的位置",hidden = true,example="1")
	private Integer signaturePage;

    //ofd属性参数
    @ApiModelProperty(value = "查找ofd reader中文字高亮显示")
    private String searchString;
    @ApiModelProperty(value = "文件的id属性")
    private String ofdId;
    @ApiModelProperty(value = "文件创建时间属性")
    private String creationDate;
    @ApiModelProperty(value = "文件创建工具")
    private String creator;
    @ApiModelProperty(value = "创建工具版本")
    private String creatorVersion;
    @ApiModelProperty(value = "title信息")
    private String title;
    @ApiModelProperty(value = "作者信息")
    private String author;
    @ApiModelProperty(value = "/文件修改时间")
    private String modDate;
    @ApiModelProperty(value = "元数据(自定义内容)")
    private Map<String, String> customDatas;

    //pdf拆分参数
    @ApiModelProperty(value = "pdf拆分起始页，默认0从第一页开始",example="0")
    private Integer splitStartPage;
    @ApiModelProperty(value = "pdf拆分终止页，默认0到最后页结束",example="0")
    private Integer splitEndPage;
    @ApiModelProperty(value = "pdf拆分分隔页数，默认0不分割，如果起始页和终止页都是0默认1",example="0")
    private Integer splitPages;
    
    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public Integer getConvertType() {
        return convertType;
    }

    public void setConvertType(Integer convertType) {
        this.convertType = convertType;
    }

    public Long getConvertTimeOut() {
        return convertTimeOut;
    }

    public void setConvertTimeOut(Long convertTimeOut) {
        this.convertTimeOut = convertTimeOut;
    }

    public String getSrcRelativePath() {
        return srcRelativePath;
    }

    public void setSrcRelativePath(String srcRelativePath) {
        this.srcRelativePath = srcRelativePath;
    }

    public String getDestRelativePath() {
        return destRelativePath;
    }

    public void setDestRelativePath(String destRelativePath) {
        this.destRelativePath = destRelativePath;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public Integer getIsDelSrc() {
        return isDelSrc;
    }

    public void setIsDelSrc(Integer isDelSrc) {
        this.isDelSrc = isDelSrc;
    }

    public String getInputUrl() {
        return inputUrl;
    }

    public void setInputUrl(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public String getAppendPath() {
        return appendPath;
    }

    public void setAppendPath(String appendPath) {
        this.appendPath = appendPath;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public Integer getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(Integer isCopy) {
        this.isCopy = isCopy;
    }

    public Integer getIsShowTitle() {
        return isShowTitle;
    }

    public void setIsShowTitle(Integer isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    public Integer[] getPage() {
        return page;
    }

    public void setPage(Integer[] page) {
        this.page = page;
    }

    public Float getZoom() {
        return zoom;
    }

    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }

    public String[] getMergeInput() {
        return mergeInput;
    }

    public void setMergeInput(String[] mergeInput) {
        this.mergeInput = mergeInput;
    }

    public String[] getBookMark() {
        return bookMark;
    }

    public void setBookMark(String[] bookMark) {
        this.bookMark = bookMark;
    }

    public String getWmContent() {
        return wmContent;
    }

    public void setWmContent(String wmContent) {
        this.wmContent = wmContent;
    }

    public Integer getWmSize() {
        return wmSize;
    }

    public void setWmSize(Integer wmSize) {
        this.wmSize = wmSize;
    }

    public Integer getWmColor() {
        return wmColor;
    }

    public void setWmColor(Integer wmColor) {
        this.wmColor = wmColor;
    }

    public String getWmFont() {
        return wmFont;
    }

    public void setWmFont(String wmFont) {
        this.wmFont = wmFont;
    }

    public Integer getWmSpace() {
        return wmSpace;
    }

    public void setWmSpace(Integer wmSpace) {
        this.wmSpace = wmSpace;
    }

    public Float getWmTransparency() {
        return wmTransparency;
    }

    public void setWmTransparency(Float wmTransparency) {
        this.wmTransparency = wmTransparency;
    }

    public Integer getWmRotate() {
        return wmRotate;
    }

    public void setWmRotate(Integer wmRotate) {
        this.wmRotate = wmRotate;
    }

    public Integer getWmSuspend() {
        return wmSuspend;
    }

    public void setWmSuspend(Integer wmSuspend) {
        this.wmSuspend = wmSuspend;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getWmTileEffect() {
        return wmTileEffect;
    }

    public void setWmTileEffect(Integer wmTileEffect) {
        this.wmTileEffect = wmTileEffect;
    }

    public Integer getWmRgb() {
        return wmRgb;
    }

    public void setWmRgb(Integer wmRgb) {
        this.wmRgb = wmRgb;
    }

    public Integer getWmRgbRed() {
        return wmRgbRed;
    }

    public void setWmRgbRed(Integer wmRgbRed) {
        this.wmRgbRed = wmRgbRed;
    }

    public Integer getWmRgbBlue() {
        return wmRgbBlue;
    }

    public void setWmRgbBlue(Integer wmRgbBlue) {
        this.wmRgbBlue = wmRgbBlue;
    }

    public Integer getWmRgbGreen() {
        return wmRgbGreen;
    }

    public void setWmRgbGreen(Integer wmRgbGreen) {
        this.wmRgbGreen = wmRgbGreen;
    }

    public String getWmPicPath() {
        return wmPicPath;
    }

    public void setWmPicPath(String wmPicPath) {
        this.wmPicPath = wmPicPath;
    }

    public Integer getIsShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(Integer isShowAll) {
        this.isShowAll = isShowAll;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsShowList() {
        return isShowList;
    }

    public void setIsShowList(Integer isShowList) {
        this.isShowList = isShowList;
    }

    public Integer getShowFooter() {
        return showFooter;
    }

    public void setShowFooter(Integer showFooter) {
        this.showFooter = showFooter;
    }

    public Integer getAcceptTracks() {
        return acceptTracks;
    }

    public void setAcceptTracks(Integer acceptTracks) {
        this.acceptTracks = acceptTracks;
    }

    public Integer getIsHideBlank() {
        return isHideBlank;
    }

    public void setIsHideBlank(Integer isHideBlank) {
        this.isHideBlank = isHideBlank;
    }

    public Integer getIsVertical() {
        return isVertical;
    }

    public void setIsVertical(Integer isVertical) {
        this.isVertical = isVertical;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
    }

    public Integer getIsShowNum() {
        return isShowNum;
    }

    public void setIsShowNum(Integer isShowNum) {
        this.isShowNum = isShowNum;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getDynamicMark() {
        return dynamicMark;
    }

    public void setDynamicMark(String dynamicMark) {
        this.dynamicMark = dynamicMark;
    }

    public String getDmXextra() {
        return dmXextra;
    }

    public void setDmXextra(String dmXextra) {
        this.dmXextra = dmXextra;
    }

    public String getDmYextra() {
        return dmYextra;
    }

    public void setDmYextra(String dmYextra) {
        this.dmYextra = dmYextra;
    }

    public String getDmFontSize() {
        return dmFontSize;
    }

    public void setDmFontSize(String dmFontSize) {
        this.dmFontSize = dmFontSize;
    }

    public Float getDmAlpha() {
        return dmAlpha;
    }

    public void setDmAlpha(Float dmAlpha) {
        this.dmAlpha = dmAlpha;
    }

    public String getDmAngle() {
        return dmAngle;
    }

    public void setDmAngle(String dmAngle) {
        this.dmAngle = dmAngle;
    }

    public String getDmFont() {
        return dmFont;
    }

    public void setDmFont(String dmFont) {
        this.dmFont = dmFont;
    }

    public String getDmColor() {
        return dmColor;
    }

    public void setDmColor(String dmColor) {
        this.dmColor = dmColor;
    }

    public Integer getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(Integer isDownload) {
        this.isDownload = isDownload;
    }

    public Integer getAntiDownload() {
        return antiDownload;
    }

    public void setAntiDownload(Integer antiDownload) {
        this.antiDownload = antiDownload;
    }

    public Integer getIsFullScreen() {
        return isFullScreen;
    }

    public void setIsFullScreen(Integer isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfAuthor() {
        return pdfAuthor;
    }

    public void setPdfAuthor(String pdfAuthor) {
        this.pdfAuthor = pdfAuthor;
    }

    public String getPdfSubject() {
        return pdfSubject;
    }

    public void setPdfSubject(String pdfSubject) {
        this.pdfSubject = pdfSubject;
    }

    public String getPdfKeyword() {
        return pdfKeyword;
    }

    public void setPdfKeyword(String pdfKeyword) {
        this.pdfKeyword = pdfKeyword;
    }

    public String getJsPath() {
        return jsPath;
    }

    public void setJsPath(String jsPath) {
        this.jsPath = jsPath;
    }

    public String[] getSourceReplace() {
        return sourceReplace;
    }

    public void setSourceReplace(String[] sourceReplace) {
        this.sourceReplace = sourceReplace;
    }

    public String[] getTargetReplace() {
        return targetReplace;
    }

    public void setTargetReplace(String[] targetReplace) {
        this.targetReplace = targetReplace;
    }

    public Integer getIsDccAsync() {
        return isDccAsync;
    }

    public void setIsDccAsync(Integer isDccAsync) {
        this.isDccAsync = isDccAsync;
    }

    public Integer getIsShowGridline() {
        return isShowGridline;
    }

    public void setIsShowGridline(Integer isShowGridline) {
        this.isShowGridline = isShowGridline;
    }

    public Integer getIsShowColWidth() {
        return isShowColWidth;
    }

    public void setIsShowColWidth(Integer isShowColWidth) {
        this.isShowColWidth = isShowColWidth;
    }

    public Integer getWmWord() {
        return wmWord;
    }

    public void setWmWord(Integer wmWord) {
        this.wmWord = wmWord;
    }

    public Long getSrcFileSize() {
        return srcFileSize;
    }

    public void setSrcFileSize(Long srcFileSize) {
        this.srcFileSize = srcFileSize;
    }

    public Integer getWmX() {
        return wmX;
    }

    public void setWmX(Integer wmX) {
        this.wmX = wmX;
    }

    public Integer getWmY() {
        return wmY;
    }

    public void setWmY(Integer wmY) {
        this.wmY = wmY;
    }

    public String getWmImage() {
        return wmImage;
    }

    public void setWmImage(String wmImage) {
        this.wmImage = wmImage;
    }

    public Integer getWmPull() {
        return wmPull;
    }

    public void setWmPull(Integer wmPull) {
        this.wmPull = wmPull;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getOfdId() {
        return ofdId;
    }

    public void setOfdId(String ofdId) {
        this.ofdId = ofdId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorVersion() {
        return creatorVersion;
    }

    public void setCreatorVersion(String creatorVersion) {
        this.creatorVersion = creatorVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public Map<String, String> getCustomDatas() {
        return customDatas;
    }

    public void setCustomDatas(Map<String, String> customDatas) {
        this.customDatas = customDatas;
    }

    public Integer getWordEdit() {
        return wordEdit;
    }

    public void setWordEdit(Integer wordEdit) {
        this.wordEdit = wordEdit;
    }

    public Integer getIsSignature() {
        return isSignature;
    }

    public void setIsSignature(Integer isSignature) {
        this.isSignature = isSignature;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

	public String getSignatureUrl() {
		return signatureUrl;
	}

	public void setSignatureUrl(String signatureUrl) {
		this.signatureUrl = signatureUrl;
	}

	public String[] getSignatureImgPath() {
		return signatureImgPath;
	}

	public void setSignatureImgPath(String[] signatureImgPath) {
		this.signatureImgPath = signatureImgPath;
	}

	public String getSignatureFolder() {
		return signatureFolder;
	}

	public void setSignatureFolder(String signatureFolder) {
		this.signatureFolder = signatureFolder;
	}

	public Integer getSignaturePage() {
		return signaturePage;
	}

	public void setSignaturePage(Integer signaturePage) {
		this.signaturePage = signaturePage;
	}

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public String getFcsCustomData() {
        return fcsCustomData;
    }

    public void setFcsCustomData(String fcsCustomData) {
        this.fcsCustomData = fcsCustomData;
    }

    public Integer getNoCache() {
        return noCache;
    }

    public void setNoCache(Integer noCache) {
        this.noCache = noCache;
    }

	public Integer getSplitStartPage() {
		return splitStartPage;
	}

	public void setSplitStartPage(Integer splitStartPage) {
		this.splitStartPage = splitStartPage;
	}

	public Integer getSplitEndPage() {
		return splitEndPage;
	}

	public void setSplitEndPage(Integer splitEndPage) {
		this.splitEndPage = splitEndPage;
	}

	public Integer getSplitPages() {
		return splitPages;
	}

	public void setSplitPages(Integer splitPages) {
		this.splitPages = splitPages;
	}
    
    
}
