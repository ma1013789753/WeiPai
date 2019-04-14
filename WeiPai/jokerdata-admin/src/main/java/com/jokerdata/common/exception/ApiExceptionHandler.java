package com.jokerdata.common.exception;

import com.jokerdata.vo.Result;
import org.apache.ibatis.logging.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author oldma
 * @date 2018年12月1日 上午10:30:00
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MyException.class)
    public Result handleRRException(MyException e) {
        System.out.println(e.toString());
        Result result = new Result();
        result.setMessage(e.getMessage());
        result.setStatus(e.getCode());
        return result;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        return Result.error500("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.error500(e.getMessage());
    }
}
