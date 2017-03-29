/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.controller;

import android.content.Context;

import com.harry.rv.common.RestError;
import com.harry.rv.model.BaseResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/26
 */
public class AppUploadController<L> extends HttpClient<L> {

    public AppUploadController(Context context,L l) {
        super(context,l);
    }

    protected abstract class UploadTask<T> extends BaseTask<File, T> {
        MultipartBody.Builder builder;
        @Override
        protected Observable<BaseResponse<T>> getObservable() {
            buildRequest();
            return null;
        }

        @Override
        public void onSuccess(BaseResponse<T> out) {
            onUploadSuccess(input, out.subjects);
        }

        @Override
        public void onErrors(RestError error) {
            onUploadFailure(input, error);
        }

        protected void buildRequest() {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), input);
            builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("file", input.getName(), requestBody);
            upload();
        }

        protected abstract void upload();

        protected abstract void onUploadFailure(File file, RestError error);

        protected abstract void onUploadSuccess(File file, T out);
    }

    protected abstract class MultiUploadTask<T> extends UploadTask<T>{
        protected File[] files;
        public MultiUploadTask(File... files){
            this.files = files;
        }

        @Override
        protected void upload() {
            for (File f : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                builder.addFormDataPart("file", f.getName(), requestBody);
            }
        }
    }
}