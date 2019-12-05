package com.neo.service.yzcloud;

import com.neo.commons.cons.IResult;
import com.neo.model.bo.FcsFileInfoBO;

/**
 * @author zhoufeng
 * @description 优云相关service
 * @create 2019-11-26 09:04
 **/
public interface IYzcloudService {

    IResult<String> uploadFileToYc(FcsFileInfoBO fcsFileInfoBO, Long userId, String cookie);
}
