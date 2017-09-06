package com.official.read.content.bean;

import java.io.Serializable;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public class JusticeBean implements Serializable {

    public static final int DISTRICT_TYPE = 1;
    public static final int TYPE_TYPE = 2;
    public static final int PRICE_TYPE = 3;
    public static final int AREA_TYPE = 4;
    public static final int STATE_TYPE = 5;

    public int id;
    public String name;
    public boolean isSelect;
    public int color;
    public int type;

    public JusticeBean(int id, String name, boolean isSelect, int color, int type) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
        this.color = color;
        this.type = type;
    }

    public JusticeBean() {

    }

    @Override
    public String toString() {
        return "JusticeBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
