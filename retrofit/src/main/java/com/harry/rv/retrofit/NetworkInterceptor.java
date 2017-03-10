/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.retrofit;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/28.
 */
public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Response originalRes=chain.proceed(request);
        String severCache=originalRes.header("Cache-Control");
        if(TextUtils.isEmpty(severCache)){
            String cacheControl=request.cacheControl().toString();
            Response res=originalRes.newBuilder().addHeader("Cache-Control",cacheControl)
                .removeHeader("Pragma")
                .build();
            return  res;
        }else {
            return  originalRes;
        }
    }
}
