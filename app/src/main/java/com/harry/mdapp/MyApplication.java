package com.harry.mdapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/9.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
