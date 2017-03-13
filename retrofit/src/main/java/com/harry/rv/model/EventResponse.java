package com.harry.rv.model;

import java.io.Serializable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class EventResponse implements Serializable {
    
    public int id;
    public String subcategory_name;
    public String category;
    public String title;
    public String image;
    public String time_str;
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventResponse) {
            return ((EventResponse) obj).id == id;
        }
        return super.equals(obj);
    }
    
}
