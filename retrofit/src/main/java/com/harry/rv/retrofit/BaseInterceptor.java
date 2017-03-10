/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        HttpUrl url=original.url().newBuilder()
            .addQueryParameter("count", 10+"")
            .build();
        RequestBody postBody=RequestBody.create(MediaType.parse("multipart/form-data"),"5");
        Request request = original.newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Connection", "keep-alive")
            .post(postBody)
            .method(original.method(), original.body())
            .url(url)
            .build();

//        if (!NetworkUtils.isConnected(RxApplication.getContext())) {
//            request = request.newBuilder()
//                .cacheControl(CacheControl.FORCE_CACHE)
//                .build();
//            // 没有网络走缓存
//        }

        return chain.proceed(request);
    }
}
