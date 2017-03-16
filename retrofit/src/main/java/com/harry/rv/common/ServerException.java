package com.harry.rv.common;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;
    
    public ServerException(int code, String msg) {
        super();
        this.code = code;
        this.message = msg;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
