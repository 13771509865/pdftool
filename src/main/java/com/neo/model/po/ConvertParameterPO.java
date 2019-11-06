package com.neo.model.po;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ConvertParameterPO implements Serializable{
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
    private String mergeInput;
    @ApiModelProperty(value = "添加书签内容")
    private String bookMark;
    @ApiModelProperty(value = "水印内容")
    private String wmContent;
    @ApiModelProperty(value = "水印字体大小",example="50")
    private Integer wmSize;
    @ApiModelProperty(value = "水印颜色",example="5")
    private Integer wmColor;
    @ApiModelProperty(value = "水印字体",example="宋体")
    private String wmFont;
    @ApiModelProperty(value = "静态水印间距",example="150")
    private Integer wmSpace;
    @ApiModelProperty(value = "静态水印透明度",example="0.5")
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
    @ApiModelProperty(value = "动态水印x轴内容之间距离",example = "150")
    private Integer dmXextra;
    @ApiModelProperty(value = "动态水印y轴内容之间距离",example = "150")
    private Integer dmYextra;
    @ApiModelProperty(value = "动态水印的文字大小",example = "50")
    private Integer dmFontSize;
    @ApiModelProperty(value = "动态水印的透明度(范围是0到1)",example="0.5")
    private Float dmAlpha;
    @ApiModelProperty(value = "动态水印旋转角度",example = "0")
    private Integer dmAngle;
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
    private String sourceReplace;
    @ApiModelProperty(value = "源文件要替换成的文字")
    private String targetReplace;
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
    private String signatureImgPath;
    @ApiModelProperty(value = "生成的的html的文件夹路径",hidden = true)
	private String signatureFolder;
    @ApiModelProperty(value = "需要更新的svg的位置",hidden = true,example="1")
	private Integer signaturePage;

    @ApiModelProperty(value = "传入后缀名(对于上传的文件没有后缀名，我们可以根据这个参数强制修改)")
    private String suffix;
    @ApiModelProperty(value = "加入图片水印，设置是否是第一页加入，默认不是，为1时就是在第一页加入",example="0")
    private Integer isFirstImage;
    @ApiModelProperty(value = "调整excel单元行的宽度，默认不调整，当为1时调整",example="0")
    private Integer isExcelWidth;
    @ApiModelProperty(value = "生成pdf是否是以绘图的方式，默认不是，设置为1是的",example="0")
    private Integer isPdfBmp;
    @ApiModelProperty(value = "对word生成word的红头文件做置灰处理，默认不置灰，1置灰",example="0")
    private Integer isAshPlacing;
    @ApiModelProperty(value = "图片转html加水印的时候，水印大小间距参照",example="0")
    private Integer imageSacleValue;
    @ApiModelProperty(value = "广告js地址引入")
	private String[] addedScriptPath;
    @ApiModelProperty(value = "图片水印x轴控制",example="0")
	private Integer wmPicX;
    @ApiModelProperty(value = "图片水印y轴控制",example="0")
	private Integer wmPicY;
    
    @ApiModelProperty(value = "图片水印文字上方")
    private Boolean wmPicIsTextUp;
    @ApiModelProperty(value = "图片水印透明度",example="0.5")
    private Float wmPicTransparency;
    @ApiModelProperty(value = "图片水印内容旋转角度",example="0")
    private Integer wmPicRotate;
    
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
    @ApiModelProperty(value = "默认是文件水印，1是图片水印",example="0")
	private Integer OFDType;
    @ApiModelProperty(value = "水印旋转角度，[-90,90]",example="45")
	private Integer OFDAngle;
    @ApiModelProperty(value = "字体大小",example="20")
	private Integer OFDFontSize;
    @ApiModelProperty(value = "水印透明度，0-1",example="0.5")
	private Float OFDAlpha;
    @ApiModelProperty(value = "水印文字")
	private String OFDContent;
    @ApiModelProperty(value = "字体名称，默认宋体",example="楷体")
    private String OFDFontName;
    @ApiModelProperty(value = "rgb颜色 空格分隔",example="192 192 192")
    private String OFDolor;
    @ApiModelProperty(value = "是否平铺，默认不是，1时平铺",example="0")
	private Integer OFDRepeat;
    @ApiModelProperty(value = "单个水印位置，0中间 1左上 2中上 3右上 4左下 5中下 6右下,默认在中间",example="0")
	private Integer OFDAlign;
    @ApiModelProperty(value = "图片水印路径",example="0")
	private String OFDImgPath;
    @ApiModelProperty(value = "图片水印位置调整参数",example="6")
	private Integer OFDMargin;
    
    //pdf拆分参数
    @ApiModelProperty(value = "pdf拆分起始页，默认0从第一页开始",example="0")
    private Integer[] splitStartPage;
    @ApiModelProperty(value = "pdf拆分终止页，默认0到最后页结束",example="0")
    private Integer[] splitEndPage;
    @ApiModelProperty(value = "pdf拆分分隔页数，默认0不分割，如果起始页和终止页都是0默认1",example="0")
    private Integer[] splitPages;
    
    @ApiModelProperty(value = "pdf转双层pdf时候的的调用ocr的地址")
    private String OCRUrl;
    
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    
}
