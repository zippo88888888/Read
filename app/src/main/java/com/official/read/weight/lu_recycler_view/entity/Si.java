package com.official.read.weight.lu_recycler_view.entity;

import com.official.read.weight.lu_recycler_view.Visitable;
import com.official.read.weight.lu_recycler_view.type.TypeFactory;

/**
 * com.official.read.weight.lu_recycler_view.entity
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class Si implements Visitable {

    public String si;
    public Si(String si) {
        this.si = si;
    }

    @Override
    public int type(TypeFactory factory) {
        return factory.type(this);
    }
}
