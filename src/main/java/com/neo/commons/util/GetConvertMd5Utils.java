package com.neo.commons.util;


import org.apache.commons.lang3.StringUtils;

import com.neo.commons.cons.SysConstant;
import com.neo.model.bo.ConvertParameterBO;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 根据文件和参数构建唯一key
 *
 * @author zhouf
 * @create 2018-12-14 11:42
 */
public class GetConvertMd5Utils {

    /**
     * @description 给异步构建md5值的,永远可以获取到md5,获取失败时为uuid
     * @param  param 转换参数
     * @author zhoufeng
     * @date 2019/5/13
     */
    public static String getNotNullConvertMd5(ConvertParameterBO param) {
        String convertMd5 = getConvertMd5(param);
        if (StringUtils.isEmpty(convertMd5)) {
            convertMd5 = SysConstant.UUIDMD5_HEADER+UUIDHelper.generateUUID();
        }
        return convertMd5;
    }

    public static String getNotNullConvertMd5(ConvertParameterBO param, String fileMd5){
        String convertMd5 = getConvertMd5(param, fileMd5);
        if (StringUtils.isEmpty(convertMd5)) {
            convertMd5 = SysConstant.UUIDMD5_HEADER+UUIDHelper.generateUUID();
        }
        return convertMd5;
    }

    public static String getConvertMd5(ConvertParameterBO param) {
        String input = param.getSrcPath();
        if (input == null || "".equals(input)) {
            return null;
        }
        File file = new File(input);
        String fileMd5 = GetFileMd5Utils.getFileMD5(file);
        return getConvertMd5(param, fileMd5);
    }

    public static String getConvertMd5(ConvertParameterBO param, String fileMd5) {
        //通过反射拿ConvertParamBO的值
        Class<ConvertParameterBO> paramBOClass = (Class) param.getClass();
        Field[] fs = paramBOClass.getDeclaredFields();
        String paramHashcode = getValidParam(fs, param);
        if (StringUtils.isNotEmpty(paramHashcode) && StringUtils.isNotEmpty(fileMd5)) {
            return fileMd5 + paramHashcode;
        } else {
            return null;
        }
    }

    //TODO 采用md5还是有碰撞概率
    //哪些参数特殊处理
    private static String getValidParam(Field[] fs, ConvertParameterBO param) {
        String paramMd5 = null;
        try {
            for (Field f : fs) {
                f.setAccessible(true); //设置属性是可以访问的
                switch (f.getName()) {
                    case "srcPath":
                    case "destPath":
                    case "srcRelativePath":
                    case "destRelativePath":
                    case "callBack":
                    case "srcFileName":
                    case "destFileName":
                    case "srcFileSize":
                    case "fileHash":
                    case "dynamicMark":
                    case "dmXextra":
                    case "dmYextra":
                    case "dmFontSize":
                    case "dmAlpha":
                    case "dmAngle":
                    case "dmFont":
                    case "dmColor":
                    case "isDelSrc":
                    case "callType":
                    case "convertId":
                    case "fcsCustomData":
                        break;
                    case "page":
                    case "bookMark":
                    case "mergeInput":
                    case "sourceReplace":
                    case "targetReplace":
                        Object val = f.get(param);
                        paramMd5 += Arrays.toString((Object[]) val);
                        break;
                    default:
                        Object val2 = f.get(param);
                        paramMd5 += val2;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SysLog4JUtils.error("获取" + param.toString() + "对象HashCode失败");
            return null;
        }
        String md5 = MD5Utils.getMD5(paramMd5);
        if (StringUtils.isNotEmpty(md5)) {
            return md5;
        } else {
            return null;
        }
    }
}
