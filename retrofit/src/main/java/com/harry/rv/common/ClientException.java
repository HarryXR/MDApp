package com.harry.rv.common;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class ClientException extends Exception {
    public int code;
    public String message;
    
    public ClientException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
