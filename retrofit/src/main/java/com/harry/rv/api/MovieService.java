/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.api;

import com.harry.rv.model.BaseResponse;
import com.harry.rv.model.EventResponse;
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
    @POST("movie/in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie();

    //正在热映
    @GET("movie/in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie(@Query("start") int start);
    //即将上映
    @GET("movie/coming_soon")
    Observable<BaseResponse<List<MovieResponse>>> getMovieComing(@Query("start") int start);
    //Top250
    @GET("movie/top250")
    Observable<BaseResponse<List<MovieResponse>>> getMovieTop(@Query("start") int start);
    //口碑榜
    @GET("movie/weekly")
    Observable<BaseResponse<List<MovieResponse>>> getMovieWeekly(@Query("start") int start);
    //北美票房榜
    @GET("movie/us_box")
    Observable<BaseResponse<List<MovieResponse>>> getMovieUS(@Query("start") int start);
    //新片榜
    @GET("movie/new_movies")
    Observable<BaseResponse<List<MovieResponse>>> getMovieNew(@Query("start") int start);
    
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
    
    //同城 event
    @GET("event/list")
    Observable<BaseResponse<List<EventResponse>>> getEventList(@Query("loc") String cityId,@Query("day_type") String
        dayType,@Query("type") String
        type);
    
    //文学 book
    @GET("book/1003078")
    Observable<BaseResponse<EventResponse>> getBook();
}
