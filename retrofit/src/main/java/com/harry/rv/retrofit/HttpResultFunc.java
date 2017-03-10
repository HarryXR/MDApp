/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.retrofit;

import com.harry.rv.model.BaseResponse;

import rx.functions.Func1;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/19.
 */
public class HttpResultFunc<T> implements Func1<BaseResponse<T>,T> {
    @Override
    public T call(BaseResponse<T> tBaseResponse) {
        return tBaseResponse.subjects;
    }
}
