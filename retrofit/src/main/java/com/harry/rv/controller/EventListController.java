package com.harry.rv.controller;

import android.content.Context;

import com.harry.rv.common.RestError;
import com.harry.rv.model.BaseResponse;
import com.harry.rv.model.EventRequest;
import com.harry.rv.model.EventResponse;

import java.util.List;

import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class EventListController extends HttpClient<EventListController.LoadListener> {
    
    public EventListController(Context context, LoadListener l) {
        super(context, l);
    }
    
    public void load(EventRequest request) {
        LoadTask task = new LoadTask();
        task.load(request);
    }
    
    public class LoadTask extends BaseTask<EventRequest, List<EventResponse>> {
        
        @Override
        protected Observable<BaseResponse<List<EventResponse>>> getObservable() {
            return service.getEventList(input.loc, input.dayType, input.type);
        }
        
        @Override
        public void onSuccess(BaseResponse<List<EventResponse>> out) {
            listener.onSuccess(out);
        }
        
        @Override
        public void onErrors(RestError error) {
            listener.onError(error);
        }
        
    }
    
    public interface LoadListener {
        void onSuccess(BaseResponse<List<EventResponse>> out);
        
        void onError(RestError e);
    }
}
