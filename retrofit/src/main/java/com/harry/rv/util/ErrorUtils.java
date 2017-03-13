package com.harry.rv.util;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.harry.rv.R;
import com.harry.rv.common.LogicError;
import com.harry.rv.common.RestError;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class ErrorUtils {
    /**
     * private constructor
     */
    private ErrorUtils() {
    }
    
    public static void toastError(Context context, RestError error) {
        String msg = getError(context, error);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    
    public static void showError(TextView tv, RestError error) {
        tv.setText(getError(tv.getContext(), error));
    }
    
    public static String getError(Context context, RestError error) {
        if (error.getCause() instanceof LogicError) {
            return ((LogicError) error.getCause()).getDesc();
        }
        int type = error.getType();
        int resId;
        if (type == RestError.TYPE_HTTP) {
            resId = R.string.error_type_request;
        }
        else if (type == RestError.TYPE_NO_CONNECTION) {
            resId = R.string.error_type_no_network;
        }
        else if (type == RestError.TYPE_TIMEOUT) {
            resId = R.string.error_type_timeout;
        }
        else if (type == RestError.TYPE_NETWORK) {
            resId = R.string.error_type_no_network;
        }
        else if (type == RestError.TYPE_SERVER) {
            resId = R.string.error_type_server;
        }
        else if (type == RestError.TYPE_PARSE) {
            resId = R.string.error_type_parse;
        }
        else {
            resId = R.string.error_type_unknown;
        }
        return context.getString(resId);
    }
}
