package com.harry.rv.model;

import java.io.Serializable;

/**
 * 活动
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class EventRequest implements Serializable {
    public String loc;//北京 108288
    public String type;// all,music, film
    public String dayType;//future, week, weekend, today, tomorrow
}
