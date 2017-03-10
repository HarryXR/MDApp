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

    /**
     * 数据
     */
    public T subjects;
    private int status;

    /**
     * 消息
     */
    private String message;
}
