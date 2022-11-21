package com.briup.crmcommon.exception;

import com.briup.crmcommon.utils.Result;
import com.briup.crmcommon.utils.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @Author lining
 * @Date 2022/10/25
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        System.out.println("配置全局异常...");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception ex) {
        System.out.println("进行异常处理...");
        //当系统发生错误：进行错误信息打印
        ex.printStackTrace();

        //当异常为Service层抛出自定义的类型ServiceException
        if (ex instanceof ServiceException) {
            ResultCode resultCode = ((ServiceException) ex).getResultCode();
            return Result.failure(resultCode);
        }
        //当异常为运行时异常，直接提示用户服务器内部错误
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
