package com.official.read.view;

import android.view.View;

import com.official.read.base.BaseView;
import com.official.read.content.bean.CollectBean;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public interface CollectView extends BaseView {

    void setCollectData(List<CollectBean> list);

    void deleteSuccess(CollectBean bean,int position);

    void skipUseAnim(CollectBean bean, View view);

    void skipNotUseAnim(CollectBean bean);
}
