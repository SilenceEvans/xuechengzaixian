package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @ClassName ExceptionCast
 * @Description 自定义异常抛出类
 * @Author wang
 * @Date 2019/08/06 17:32:48
 * @Version 1.0
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
