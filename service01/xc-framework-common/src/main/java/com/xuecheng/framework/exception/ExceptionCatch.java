package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ExceptionCatch
 * @Description 自定义异常捕获类
 * @Author wang
 * @Date 2019/08/06 17:35:00
 * @Version 1.0
 */
@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //定义一个存放个未知异常的集合
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //使用builder来构建未知异常和错误异常的集合
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();
    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    /**
     * 异常处理方法
     * 异常处理注解
     * @param customException 自定义异常类对象
     * @return 响应结果
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        //记录日志
        LOGGER.error("catch exception:{}",customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    /**
     * 处理未知异常的方法
     * @param exception 未知异常
     * @return 响应结果
     * 处理未知异常时先从构建出来的map里进行查找，看是否有对应的异常存在，有则返回值为该异常
     * 异常，没有则统一返回99999
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        LOGGER.error("catch exception:{}",exception.getMessage());
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
            //从集合中查找是否有对应的异常
            ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
            if (resultCode == null){
                //如果没有则返回99999
                return new ResponseResult(CommonCode.SERVER_ERROR);
            }else {
                return new ResponseResult(CommonCode.INVALID_PARAM);
            }
        }
}
