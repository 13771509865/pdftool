/**
 * Description:
 * Date: 2017年12月21日
 * Author: zhao_yuanchao
 */
package com.neo.commons.cons.constants;

import java.io.File;

public class PathConsts {
	public static final String ASTERISK			= "*";// 英文星号
	public static final String AT				= "@";//英文@
	public static final String COMMA				= ",";//英文逗号
	public static final String Split_Char = "!";//分割字符串使用
	public static final String Cloud_Separator = "/";//云存储上的分隔符

	// 高宽、方向、颜色类型、dpi
	public static final String WIDTH			= "width";
	public static final String HEIGHT		= "height";
	public static final String MEDIAINFO		= "mediainfo";
	public static final String ORIENTATION 	= "orientation";
	public static final String COLORTYPE		= "colorType";
	public static final String WIDTHDPI		= "widthDpi";
	public static final String HEIGHTDPI		= "heightDpi";

	// 行列
	public static final String ROW			= "row";
	public static final String COLUMN		= "column";

	public static final String ZIP 			= "zip";
	public static final String AVATAR		= "avatar";
	public static final String USER			= "user";
	public static final String DOCUMENT 		= "document";
	// 水印pdf: watermarkFolder/a4ad66359d3fe32ba4ad66359d3fe32b.pdf 模板: watermarkFolder/md5(userId_fileId_version_水印文字).pdf
	// 私有云不切图的水印图片: watermarkFolder/a4ad66359d3fe32ba4ad66359d3fe32b.jpg 模板: watermarkFolder/md5(userId_fileId_version_水印文字).jpg
	// 私有云切图的水印图片: watermarkFolder/a4ad66359d3fe32ba4ad66359d3fe32b/a4adad66359d366359d3fe32ba4fe32b.jpg
	// 		模板: watermarkFolder/md5(userId_fileId_version_水印文字)/md5(scale_row_column).jpg
	public static final String WatermarkFolder	= "watermarkFolder";


	public static final String KeynoteExt 	= "key";
	public static final String NumbersExt 	= "numbers";
	public static final String PagesExt		= "pages";

	public static final String ThumbFolder 		= "thumbnail";// pdf缩略图文件夹
	public static final String AttachmentFolder 	= "attachment";// 语音等附件的文件夹
	public static final String SliceFolder 		= "slice";// 大图片的切片文件夹
	public static final String TransferFolder 	= "transfer";// 传递文件文件夹
	public static final String Default_File_Folder = "default_file";//默认文件所在的文件夹

	public static final String JPG	= "jpg";//超过20M的图片转换为jpeg预览
	public static final String PNG	= "png";//转换的png图片
	public static final String PDF	= "pdf";
	public static final String PSD	= "psd";
	public static final String TIF	= "tif";
	public static final String TIFF	= "tiff";
	public static final String MP3	= "mp3";
	public static final String MP4	= "mp4";
	public static final String DAE	= "dae";
	public static final String DOCX	= "docx";
	public static final String DOC	= "doc";

	public static final String PdfFromImage			= "PdfFromImage";//图片pdf
	public static final String BigImageDisplaySuffix	= "display.jpg";// 大于20M的图片会生成一个缩小的jpg预览图
	public static final String ImageFromPdf			= "imageFromPdf";//图片pdf转换的图片

	public static final String ConvertPDFSuffix 		= "_convert.pdf";//转换后的pdf文件添加的后缀, 服务器端使用.
	public static final String ConvertPNGSuffix 		= "_convert.png";//psd文件转换的png文件的后缀, 服务器端使用.
	public static final String ConvertAudioSuffix	= "_convert.mp3";//转换后的MP3文件添加的后缀, 服务器端使用.
	public static final String ConvertVideoSuffix 	= "_convert.mp4";//转换的MP4文件的后缀, 服务器端使用.
	public static final String ConvertThreeDSuffix 	= "_convert.dae";//转换的dae文件的后缀, 服务器端使用.

	//水印相关
	public static final String WaterMarkPdfFile 		= "watermarkFile.pdf";//把转换的文件copy一份并添加水印时的前缀 (弃用)
	public static final String WatermarkPreviewSrc	= "previewSrc.jpg";
	public static final String AliyunWatermarkFolder	= "watermark";//命名规则为 watermark/ + 企业id + ".png"
	public static final String LocalWatermarkFolder	= "images" + File.separator + "watermark";  // (弃用)
}
