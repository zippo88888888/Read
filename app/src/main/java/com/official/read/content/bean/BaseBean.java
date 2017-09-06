package com.official.read.content.bean;

import java.io.Serializable;
import java.util.List;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class BaseBean<T> implements Serializable {

    public String info;
    public CommonBean<T> data;
    public int status;
    public int version;

}
