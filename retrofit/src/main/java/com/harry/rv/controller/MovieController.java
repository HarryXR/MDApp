/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.controller;

import android.content.Context;

import com.harry.rv.model.BaseResponse;
import com.harry.rv.model.MovieResponse;
import com.harry.rv.retrofit.MovieRequest;

import java.util.List;

import rx.Observable;


/**
 * 类/接口描述
 *
 * @author Harry
 */
public class MovieController extends HttpClient<MovieController.LoadListener> {

    public MovieController(Context context,LoadListener l) {
        super(context,l);
    }

    public void load(MovieRequest request) {
        LoadTask task = new LoadTask();
        task.load(request);
    }

    private class LoadTask extends BaseTask<MovieRequest,List<MovieResponse>> {

        @Override
        public Observable<BaseResponse<List<MovieResponse>>> getObservable() {
            return service.getMovie(input.start);
        }

        @Override
        public void onSuccess(List<MovieResponse> out) {
            listener.onSuccess(out);
        }

        @Override
        public void onErrors(Throwable error) {
            listener.onError(error);
        }

        @Override
        public void onComplete() {
            listener.onComplete();
        }
    }

    public interface LoadListener {
        void onSuccess(List<MovieResponse> out);

        void onError(Throwable error);

        void onComplete();
    }
}