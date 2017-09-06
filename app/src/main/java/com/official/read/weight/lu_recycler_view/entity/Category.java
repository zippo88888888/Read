package com.official.read.weight.lu_recycler_view.entity;

import com.official.read.weight.lu_recycler_view.type.TypeFactory;
import com.official.read.weight.lu_recycler_view.Visitable;

/**
 * com.official.read.weight.lu_recycler_view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class Category implements Visitable {

    public String title;

    public Category(String title) {
        this.title = title;
    }

    @Override
    public int type(TypeFactory factory) {
        return factory.type(this);
    }
}
