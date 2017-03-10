/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class MovieResponse implements Serializable {
    public String id;
    public String alt;
    public String year;
    public String title;
    public String original_title;
    public List<String> genres;
    public List<Cast> casts;
    public List<Cast> directors;
    public Avatars images;

    public class Cast implements Serializable {
        public String id;
        public String name;
        public String alt;
        public Avatars avatars;
    }

    public class Avatars implements Serializable {
        public String small;
        public String medium;
        public String large;
    }

    @Override
    public boolean equals(Object o) {
        return this.id.equals(((MovieResponse) o).id);
    }
}
