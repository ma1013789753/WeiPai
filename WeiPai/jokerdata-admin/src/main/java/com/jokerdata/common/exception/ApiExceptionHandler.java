package com.jokerdata.common.exception;

import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 异常处理器
 *
 * @author oldma
 * @date 2018年12月1日 上午10:30:00
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MyException.class)
    public Result handleRRException(MyException e) {
        Result result = new Result();
        result.setMessage(e.getMessage());
        result.setStatus(e.getCode());
        return result;
    }
    @ExceptionHandler(ApiException.class)
    public ApiResult handleRRException(ApiException e) {

        return ApiResult.error(e.getMsg());
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        return Result.error500("数据库中已存在该记录");
    }

//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception e) {
//        return Result.error500(e.getMessage());
//    }

    /**
     * 验证异常
     * @param req
     * @param e
     * @return
     * @throws MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResult handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "Invalid Request:\n";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "\n";
        }

        logger.info("MethodArgumentNotValidException",e);
        return ApiResult.error(errorMesssage);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ApiResult handleBindException(BindException e) throws BindException {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField())
                .append(fieldError.getDefaultMessage());
        // 生成返回结果
        logger.info("BindException", e);
        return ApiResult.error(sb.toString());
    }

}

