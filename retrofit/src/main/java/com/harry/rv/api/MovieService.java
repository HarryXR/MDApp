/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.api;

import com.harry.rv.model.BaseResponse;
import com.harry.rv.model.MovieResponse;
import com.harry.rv.model.PostResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public interface MovieService {
    @POST("in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie();

    @GET("in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie(@Query("start") int start);

    @Multipart
    @POST("face")
    Observable<BaseResponse<PostResponse>> upload(@Part("file\"; filename=\"microMsg.1460895294032.jpg\"") RequestBody
                                                  imgs);

    @POST("face")
    Observable<BaseResponse<PostResponse>> upload(@Body MultipartBody
                                                      imgs);

    @GET("20130116/84481_20130116142820494200_1.jpg")
    Observable<ResponseBody> download();

    @Streaming
    @GET()
    Observable<ResponseBody> download(@Url String url);
}
