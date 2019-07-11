package com.neo.service.convertParameterBO;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.GetConvertMd5Utils;
import com.neo.commons.util.SpringUtils;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.SysConfig;
import com.neo.model.bo.ConvertParameterBO;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("convertParameterBOService")
public class ConvertParameterBOService {

    @Autowired
    private SysConfig sysConfig;

    /**
     * @param convertBO 转换参数
     * @return callback为空返回fail
     * @description 异步时检查callback
     * @author zhoufeng
     * @date 2019/1/28
     */
    public IResult<ResultCode> checkCallBack(ConvertParameterBO convertBO) {
        String callBack = convertBO.getCallBack();
        if (StringUtils.isEmpty(callBack)) {
            return DefaultResult.failResult(ResultCode.E_INVALID_PARAM.getInfo(), ResultCode.E_INVALID_PARAM);
        } else {
            return DefaultResult.successResult();
        }
    }

    public void preCorrectConvertParameterBO(ConvertParameterBO convertBO){
        Long convertTimeOut = convertBO.getConvertTimeOut();
        if (convertTimeOut == null) { //默认配置转换超时时间
            convertBO.setConvertTimeOut(Long.valueOf(sysConfig.getConvertTimeout()));
        }
        //缩放比例
        if (convertBO.getZoom() != null && convertBO.getZoom() <= 0) {
            convertBO.setZoom(1F);
        }
        if (convertBO.getWmSize() != null && convertBO.getWmSize() <= 0) {
            convertBO.setWmSize(100);
        }
        if (convertBO.getWmTransparency() != null && (convertBO.getWmTransparency() < 0 || convertBO.getWmTransparency() > 1)) {
            convertBO.setWmTransparency(1F);
        }
        if (convertBO.getDmAlpha() != null && (convertBO.getDmAlpha() < 0 || convertBO.getDmAlpha() > 1)) {
            convertBO.setDmAlpha(0.5F);
        }
    }

    /**
     * 构建ConvertParameterBO对象
     *
     * @param convertBO 转换参数
     * @author zhouf
     */
    public void buildConvertParameterBO(ConvertParameterBO convertBO) {
        preCorrectConvertParameterBO(convertBO);
        String srcRelative = convertBO.getSrcRelativePath();
        if (!StringUtils.isEmpty(srcRelative)) {
            String srcRoot = sysConfig.getInputDir();
            if (convertBO.getIsSignature() != null && 1 == convertBO.getIsSignature()) {  //签批源文件在output下
                convertBO.setSignatureUrl(SpringUtils.getApplicationName() + SysConstant.SIGNATURE_INTERFACE);
                if (StringUtils.isNotEmpty(convertBO.getSignatureImgPath())) {
                    srcRoot = sysConfig.getOutputDir();
                }
            }
            convertBO.setSrcPath(srcRoot + File.separator + srcRelative);
            convertBO.setSrcFileName(FilenameUtils.getName(srcRelative));
            File srcFile = new File(srcRoot, srcRelative);

            if (srcFile.isFile()) {
                convertBO.setSrcFileSize(srcFile.length());
            }
        }
        //TODO 纠正type，如果真实纠正清空convertBO的fileHash,判断纠正后缀从SrcFileName中拿
//        Integer fixConvertType =EnumConvertType.fixConvertType(convertBO.getConvertType(), FilenameUtils.getExtension(convertBO.getSrcFileName()));
//        if(!fixConvertType.equals(convertBO.getConvertType())) {
//        	convertBO.setConvertType(fixConvertType);
//        	convertBO.setFileHash("");
//        }

        if(StringUtils.isEmpty(convertBO.getFileHash())){ //从组合接口过来的就不用变fileHash
            convertBO.setFileHash(GetConvertMd5Utils.getNotNullConvertMd5(convertBO));   //服务于组合接口中构建viewUrl
        }
    }

    /**
     * 检查ConvertParameterBO参数合法性
     *
     * @param convertBO 转换参数
     * @return 检查结果
     * @author zhouf
     */
    public IResult<ResultCode> checkParam(ConvertParameterBO convertBO) {
        String srcPath = convertBO.getSrcPath();
        String fileName = convertBO.getSrcFileName();
        Integer convertType = convertBO.getConvertType();
        boolean inputDir = srcPath != null && !"".equals(srcPath);
        boolean checkInput = inputDir ? new File(srcPath).exists() : false;
        boolean checkFileName = fileName != null && !"".equals(fileName);
        boolean checkType = EnumConvertType.getEnum(convertType) != null;
        if (checkInput && checkFileName && checkType) {
            if (new File(srcPath).length() > sysConfig.getUploadSize() * 1024 * 1024) {//文件过大
                return DefaultResult.failResult(ResultCode.E_UPLOAD_SIZE.getInfo(), ResultCode.E_UPLOAD_SIZE);
            }
            if(StringUtils.isEmpty(convertBO.getFileHash()) || convertBO.getFileHash().startsWith(SysConstant.UUIDMD5_HEADER)){
                return DefaultResult.failResult(ResultCode.E_GETCONVERTMD5_FAIL.getInfo(),ResultCode.E_GETCONVERTMD5_FAIL);
            }
            if (!checkIllegal(convertBO)) { //有非法字符
                return DefaultResult.failResult(ResultCode.E_ILLEGA_PARAM.getInfo(), ResultCode.E_ILLEGA_PARAM);
            }
            if (!checkSrcFileByConvertType(convertType, fileName)) {
                return DefaultResult.failResult(ResultCode.E_CONVERTYPE_SRCFILE_ERROR.getInfo(), ResultCode.E_CONVERTYPE_SRCFILE_ERROR);
            }
            //如果是合并文档 还要检查 被合并的文档是否存在
            if (EnumConvertType.isMerge(convertBO.getConvertType())) {
                String[] mergeInput = convertBO.getMergeInput();
                if (mergeInput == null) {
                    return DefaultResult.failResult(ResultCode.E_MERGEFILE_NULL);
                } else {
                    for (int i = 0; i < mergeInput.length; i++) {
                        File mergeFile = new File(sysConfig.getInputDir(), mergeInput[i]);
                        if (!mergeFile.isFile()) {
                            return DefaultResult.failResult(ResultCode.E_MERGEFILE_NULL);
                        } else {
                            mergeInput[i] = mergeFile.getAbsolutePath();
                        }
                    }
                    convertBO.setMergeInput(mergeInput); //更新
                }
            }
            //图片水印,图片是否存在
            String wmPicPath = convertBO.getWmPicPath();
            if (!StringUtils.isEmpty(wmPicPath)) {
                File wmPicFile = new File(sysConfig.getInputDir(), wmPicPath);
                if (!wmPicFile.isFile()) {
                    return DefaultResult.failResult(ResultCode.E_WMPICFILE_ERROE);
                } else {
                    convertBO.setWmPicPath(wmPicFile.getAbsolutePath()); //更新
                }
            }
            return DefaultResult.successResult();
        } else {
            SysLog4JUtils.info("FcsCloud返回无效参数,checkInput,checkFileName,checkType依次为:" + checkInput + "," + checkFileName + "," + checkType);
            return DefaultResult.failResult(ResultCode.E_INVALID_PARAM.getInfo(), ResultCode.E_INVALID_PARAM);
        }
    }

    //不能影响fileMd5
    public IResult<ResultCode> prepareConvert(ConvertParameterBO convertBO) {
        setDestFileByType(convertBO);
        updateConvertBOSeparator(convertBO);
        //提前操作
        IResult<String> preResult = preFileOperation(convertBO);
        if (preResult.isSuccess()) {
            return DefaultResult.successResult();
        } else {
            return DefaultResult.failResult(preResult.getMessage(), ResultCode.E_FILESERVICE_FAIL);
        }
    }

    /**
     * @param convertType 转换类型
     * @return 合法返回true
     * @description 检查源文件和convertType的关系, 如pdf, ofd文件检查
     * @Param srcFileName 源文件名
     * @author zhoufeng
     * @date 2019/4/3
     */
    private Boolean checkSrcFileByConvertType(Integer convertType, String srcFileName) {
        //TODO 就判断了pdf和ofd可以想下更好的写法
        String extension = FilenameUtils.getExtension(srcFileName);//文件后缀
        if (StringUtils.isNotEmpty(extension)) {
            switch (extension.toLowerCase()) {
                case "ofd":
                    return EnumConvertType.isOfdOperation(convertType);
                case "pdf":
                    return EnumConvertType.isPdfOperation(convertType);
                case "zip":
                case "rar":
                case "tar":
                case "7z":
                case "gz":
                    return EnumConvertType.ZIP_HTML_TEMP.getValue() == convertType;
                default:
                    return true;
            }
        }
        return true;     //没有后缀直接为true
    }

    private String updateFileSeparator(String path) {
        if (!StringUtils.isEmpty(path)) {
            path = path.replaceAll("(\\\\|/)", Matcher.quoteReplacement(File.separator));
        }
        return path;
    }

    /**
     * @param convertBO 转换参数
     * @description 统一正反斜杆
     * @author zhoufeng
     * @date 2019/2/15
     */
    private void updateConvertBOSeparator(ConvertParameterBO convertBO) {
        convertBO.setSrcPath(updateFileSeparator(convertBO.getSrcPath()));
        convertBO.setSrcRelativePath(updateFileSeparator(convertBO.getSrcRelativePath()));
        convertBO.setDestPath(updateFileSeparator(convertBO.getDestPath()));
        convertBO.setDestRelativePath(updateFileSeparator(convertBO.getDestRelativePath()));
    }

    /**
     * 创建父文件夹,创建BO文件
     *
     * @param convertBO 参数
     */
    private IResult<String> preFileOperation(ConvertParameterBO convertBO) {
        Boolean mkResult = true;
        if (mkResult) {
            boolean mkBOFile = mkBOFile(convertBO);
            if (mkBOFile) {
                return DefaultResult.successResult();
            } else {
                return DefaultResult.failResult("创建转换BO文件失败");
            }
        } else {
            return DefaultResult.failResult("创建父文件夹失败");
        }
    }

    private boolean mkBOFile(ConvertParameterBO convertBO) {
        try {
            String paramStr = convertBO.toString();
            File rootFile = new File(sysConfig.getInputDir(), SysConstant.CONVERTPARAMETERDIR);
            File paramFile = new File(rootFile, convertBO.getFileHash() + File.separator + SysConstant.CONVERTPARAMETERFILENAME);
            FileUtils.writeStringToFile(paramFile, paramStr, Charsets.UTF_8);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SysLog4JUtils.error("生成BO.txt文件失败", e);
            return false;
        }
    }

    /**
     * @param convertBO 转换参数
     * @description 检查非法, 非法为false
     * @author zhoufeng
     * @date 2019/1/25
     */
    private Boolean checkIllegal(ConvertParameterBO convertBO) {
        Boolean result1 = checkIllegalFileName(convertBO);
        String srcPath = convertBO.getSrcPath();
        String appendPath = convertBO.getAppendPath();
        String[] mergeInput = convertBO.getMergeInput();
        Boolean result2 = checkIllegalPath(srcPath);
        Boolean result3 = checkIllegalPath(appendPath);
        Boolean result4 = true;
        if (mergeInput != null) {
            for (String merge : mergeInput) {
                if (!checkIllegalPath(merge)) {
                    result4 = false;
                }
            }
        }
        return (result1 && result2 && result3 && result4);
    }

    /**
     * @param convertBO 转换参数
     * @description 检查自定义文件名是否合法, 非法为false
     * @author zhoufeng
     * @date 2019/1/25
     */
    private Boolean checkIllegalFileName(ConvertParameterBO convertBO) {
        String destinationName = convertBO.getDestinationName();
        if (!StringUtils.isEmpty(destinationName)) {
            Matcher matcher = Pattern.compile("[^/\\\\<>:*?|\"]+").matcher(destinationName); // 文件名不能包含<>/\|:"*?
            return matcher.matches();
        }
        return true;
    }

    /**
     * @param path 待检查路径
     * @description 检查是否包含../,包含为false
     * @author zhoufeng
     * @date 2019/1/25
     */
    private Boolean checkIllegalPath(String path) {
        if (!StringUtils.isEmpty(path)) {
            Pattern p = Pattern.compile("\\.\\./|\\.\\.\\\\");
            Matcher m = p.matcher(path);
            return !(m.find());
        }
        return true;
    }

    /**
     * 根据转换类型设置输出相关数据
     *
     * @param convertBO 转换参数
     * @author zhouf
     */
    private void setDestFileByType(ConvertParameterBO convertBO) {
        EnumConvertType enumType = EnumConvertType.getEnum(convertBO.getConvertType());
        if (enumType != null) {
            String desRoot = sysConfig.getOutputDir();
            String destRelativePath;
            String destPath;
            String fileName = FilenameUtils.getBaseName(convertBO.getSrcFileName()); //源文件名
            String tempName;
            String tempRelative;
            if (!StringUtils.isEmpty(convertBO.getAppendPath())) {
                tempRelative = convertBO.getAppendPath();
            } else {
                tempRelative = DateViewUtils.format(new Date(), sysConfig.getFolderFormat());
            }
            if (!StringUtils.isEmpty(convertBO.getDestinationName())) {
                tempName = convertBO.getDestinationName();
            } else {
                tempName = convertBO.getFileHash();
            }
            String targetType = enumType.getExt(); // 根据转换类型获取文件后缀
            if (enumType.isMultiType()) {
                // 如果是多文件类型
                destRelativePath = tempRelative + File.separator + tempName;
                destPath = desRoot + File.separator + destRelativePath;
            } else {
                //如果是模板形式保持原文件后缀
                if (enumType.isTemplate()) {
                    targetType = FilenameUtils.getExtension(convertBO.getSrcFileName());
                }
                fileName += "." + targetType;
                destRelativePath = tempRelative + File.separator + tempName + File.separator + fileName;
                destPath = desRoot + File.separator + destRelativePath;
            }
            convertBO.setDestFileName(fileName);
            convertBO.setDestRelativePath(destRelativePath);
            convertBO.setDestPath(destPath);
        }
    }
}
