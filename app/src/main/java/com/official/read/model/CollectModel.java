package com.official.read.model;

import com.official.read.content.bean.CollectBean;

import java.util.List;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public interface CollectModel {

    List<CollectBean> getCollectData();

    boolean getCancelCollectState();
}
