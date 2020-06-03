package com.neo.web.update;


import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.update.IUpdateService;
import com.yozosoft.api.order.dto.ServiceAppUserRightDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 更新数据接口
 * @author xujun
 * @description
 * @create 2020年06月01日
 */
@Api(value = "数据更新Controller", tags = {"数据更新Controller"})
@RestController(value = "/update")
public class UpdateController {

    @Autowired
    private IUpdateService iUpdateService;


    @ApiOperation(value = "auth数据更新")
    @PostMapping(value = "/auth")
    public ResponseEntity authUpdate(@RequestBody ServiceAppUserRightDto serviceAppUserRightDto, @RequestParam long userId, @RequestParam String nonce, @RequestParam String sign){
        try {
            ResponseEntity result = iUpdateService.authUpdate(serviceAppUserRightDto,userId, nonce, sign);
            SysLogUtils.info("[userId："+userId+"开始更新数据],更新结果："+result.getStatusCode());
            return result;
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
