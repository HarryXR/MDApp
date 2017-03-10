/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv;

import android.app.Application;
import android.content.Context;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/28.
 */
public class RxApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
