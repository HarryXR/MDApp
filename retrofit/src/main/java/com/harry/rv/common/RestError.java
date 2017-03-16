package com.harry.rv.common;

import android.accounts.NetworkErrorException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class RestError extends Exception {
    public static final int TYPE_NO_CONNECTION = 1;
    public static final int TYPE_TIMEOUT = 2;
    public static final int TYPE_NETWORK = 3;
    public static final int TYPE_HTTP = 4;
    public static final int TYPE_SERVER = 5;
    public static final int TYPE_PARSE = 16;
    public static final int TYPE_CLIENT = 17;
    public int mErrorType;
    
    public RestError(Throwable error) {
        super(error);
        this.initType();
    }
    
    private void initType() {
        Throwable error = this.getCause();
        if(error != null) {
            if(error instanceof HttpException) {
                this.mErrorType = TYPE_HTTP;
            } else if(error instanceof ConnectException) {
                this.mErrorType = TYPE_NO_CONNECTION;
            } else if(error instanceof TimeoutException) {
                this.mErrorType = TYPE_TIMEOUT;
            } else if(error instanceof SocketTimeoutException) {
                this.mErrorType = TYPE_TIMEOUT;
            }
            else if(error instanceof NetworkErrorException) {
                this.mErrorType = TYPE_NETWORK;
            } else if(error instanceof ServerException) {
                this.mErrorType = TYPE_SERVER;
            } else if(error instanceof JsonParseException
                || error instanceof JSONException
                || error instanceof ParseException) {
                this.mErrorType = TYPE_PARSE;
            } else if(error instanceof ClientException) {
                this.mErrorType = TYPE_CLIENT;
            } else {
                this.mErrorType = 0;
            }
            
        }
    }
    
    public int getType() {
        return this.mErrorType;
    }
}
