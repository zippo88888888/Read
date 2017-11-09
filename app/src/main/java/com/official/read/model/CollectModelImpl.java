package com.official.read.model;

import com.official.read.content.bean.CollectBean;
import com.official.read.util.SPUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public class CollectModelImpl implements CollectModel {

    @Override
    public List<CollectBean> getCollectData() {
        return DataSupport.findAll(CollectBean.class);
    }

    @Override
    public boolean getCancelCollectState() {
        return (boolean) SPUtil.get(SPUtil.CANCEL_COLLECT, false);
    }
}
