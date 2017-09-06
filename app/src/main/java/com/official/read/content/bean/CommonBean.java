package com.official.read.content.bean;

import java.util.List;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/8/3.
 * description: 我为什么要这么写了
 * version: 1.0
 */

public class CommonBean<T> {

    // 与BaseBean通用
    public int total_page;
    public List<T> list;

    // 单独使用
    public T data;
    public String info;
    public int status;
    public int version;

}
