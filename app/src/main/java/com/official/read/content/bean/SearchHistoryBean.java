package com.official.read.content.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * com.official.read.content.bean
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class SearchHistoryBean extends DataSupport implements Serializable {

    public String name;
    public Date time;
}
