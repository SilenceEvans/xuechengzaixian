package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @ClassName CustomException
 * @Description 自定义异常捕获类
 * @Author wang
 * @Date 2019/08/06 16:14:56
 * @Version 1.0
 */
public class CustomException extends RuntimeException {
    private ResultCode resultCode;
    public CustomException (ResultCode resultCode){
        super("错误代码"+resultCode.code()+"错误信息"+resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
