/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.model;

import java.io.Serializable;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class BaseResponse<T> implements Serializable {
    public int count;
    public int start;
    public int total;
    public int code;
    /**
     * 错误消息
     */
    public String msg;

    /**
     * 数据
     */
    public T subjects;
    public T events;
    private int status;
    
}
