/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.controller;

import android.content.Context;

import com.harry.rv.model.BaseRequest;
import com.harry.rv.model.BaseResponse;
import com.harry.rv.model.PostResponse;

import java.io.File;

import okhttp3.Headers;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/28.
 */
public class UploadController extends AppUploadController<UploadController.LoadListener> {
    public UploadController(Context context,LoadListener o) {
        super(context,o);
    }

    public void save(BaseRequest request, File... files) {
        SaveTask task = new SaveTask(request, files);
        task.load(files[0]);
    }

    public class SaveTask extends MultiUploadTask<PostResponse> {
        BaseRequest req;

        public SaveTask(BaseRequest request) {
            req = request;
        }

        public SaveTask(BaseRequest request, File... file) {
            this(request);
            files = file;
        }

        @Override
        protected Observable<BaseResponse<PostResponse>> getObservable() {
            super.getObservable();
            return service.upload(builder.build());
        }

        @Override
        protected void onUploadFailure(File file, Throwable error) {
            listener.onError(error);
        }

        @Override
        protected void onUploadSuccess(File file, PostResponse out) {
            listener.onSuccess(out);
        }

        @Override
        protected void upload() {
            super.upload();
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=uid"),
                RequestBody.create(null, req.uid + ""));
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=token"),
                RequestBody.create(null, req.token));
        }
    }

    public interface LoadListener {
        void onSuccess(PostResponse out);

        void onError(Throwable error);

        void onComplete();
    }
}
