package com.neo.service.yzcloud;

import com.neo.commons.cons.IResult;

/**
 * @author zhoufeng
 * @description 优云相关service
 * @create 2019-11-26 09:04
 **/
public interface IYzcloudService {

    IResult<String> uploadFileToYc(String targetRelativePath);
}
