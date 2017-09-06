package com.official.read.weight.lu_recycler_view.entity;

import com.official.read.weight.lu_recycler_view.type.TypeFactory;
import com.official.read.weight.lu_recycler_view.Visitable;

import java.util.List;

/**
 * com.official.read.weight.lu_recycler_view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class HotList implements Visitable {

    public List<Product> products;

    public HotList(List<Product> products) {
        this.products = products;
    }

    @Override
    public int type(TypeFactory factory) {
        return factory.type(this);
    }
}
