package com.harry.rv.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.harry.rv.common.RestError;
import com.harry.rv.common.ServerException;
import com.harry.rv.model.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;
    
    public MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        this.adapter = adapter;
    }
    
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseResponse re = mGson.fromJson(response, BaseResponse.class);
        //关注的重点，自定义响应码中非0的情况，一律抛出ServerException异常。
        //这样，我们就成功的将该异常交给onError()去处理了。
        if (re.code > 0) {
            value.close();
            try {
                throw new RestError(new ServerException(re.code, re.msg));
            } catch (RestError restError) {
                restError.printStackTrace();
            }
        }
        
        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(bis,charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
