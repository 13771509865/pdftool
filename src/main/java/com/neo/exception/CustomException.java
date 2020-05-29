package  com.neo.exception;

import com.neo.commons.cons.EnumResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoufeng
 * @description 自定义异常父类
 * @create 2019-01-28 10:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CustomException extends RuntimeException {
    /**
     * http返回状态码,默认200
     */
    private Integer httpCode = 200;

    /**
     * 异常状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常内容
     */
    private Object data = null;

    public CustomException(EnumResultCode resultCode) {
        this.code = resultCode.getValue();
        this.message = resultCode.getInfo();
    }

    public CustomException(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CustomException(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
