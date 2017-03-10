/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.controller;

import android.content.Context;

import com.harry.rv.api.MovieService;
import com.harry.rv.model.BaseResponse;
import com.harry.rv.retrofit.BaseInterceptor;
import com.harry.rv.retrofit.HttpResultFunc;
import com.harry.rv.retrofit.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class HttpClient<L> {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";//http://api-test.mainaer.com/v3.0/
    Retrofit retrofit;
    MovieService service;
    Cache cache;
    L listener;
    Context context;

    public HttpClient(Context context) {
        this.context = context;
        cache = new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024);//10M
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.addInterceptor(new BaseInterceptor()).addNetworkInterceptor(new NetworkInterceptor()).cache(cache);
        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        service = retrofit.create(MovieService.class);
    }

    public void setListener(L l) {
        this.listener = l;
    }

    public HttpClient(Context context, L l) {
        this(context);
        setListener(l);
    }

    protected abstract class BaseTask<Input, T> {

        Observable<BaseResponse<T>> observable;
        Input input;

        protected void load(Input input) {
            this.input = input;
            observable = getObservable();
            observable.map(new HttpResultFunc<T>()).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    onErrors(e);
                }

                @Override
                public void onNext(T res) {
                    onSuccess(res);
                }
            });
        }

        protected abstract Observable<BaseResponse<T>> getObservable();

        public abstract void onSuccess(T out);

        public abstract void onErrors(Throwable error);

        public abstract void onComplete();
    }
}
