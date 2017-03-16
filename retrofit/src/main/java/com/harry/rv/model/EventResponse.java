package com.harry.rv.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class EventResponse implements Serializable,Comparable<EventResponse>{
    
    public int id;
    public String subcategory_name;
    public String category=" ";
    public String title;
    public String image;
    public String image_hlarge;
    public String time_str;
    
    public String initial=" ";
    
    public String getInitial() {
        return initial;
    }
    
    public void setInitial(String initial) {
        this.initial = initial;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventResponse) {
            return ((EventResponse) obj).id == id;
        }
        return super.equals(obj);
    }
    
    @Override
    public int compareTo(@NonNull EventResponse o) {
        if(o != null){
            getInitial().compareTo(o.getInitial());
        }
        return 0;
    }
}
