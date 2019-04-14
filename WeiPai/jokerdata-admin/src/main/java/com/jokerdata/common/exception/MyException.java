package com.jokerdata.common.exception;


import com.jokerdata.common.utils.ConstCode;

import java.io.Serializable;

/**
 * 自定义异常
 *
 * @author oldma
 * @date 2018年11月1日 上午10:30:00
 */
public class MyException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 错误码
     */
    private int code = ConstCode.CODE_500;

    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MyException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MyException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MyException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}