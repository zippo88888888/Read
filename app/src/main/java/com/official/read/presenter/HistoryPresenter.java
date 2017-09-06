package com.official.read.presenter;

import android.view.View;
import android.widget.TextView;

import com.official.read.content.bean.JusticeBean;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public interface HistoryPresenter {

    void getHistoryData(int city, int building_type, int down_payment, int area, int page);

    List<JusticeBean> getJusticeDataByID(int groupButtonID);

    void checkJusticeBean(JusticeBean bean);

    void fuckSetColor(TextView cjMoney);

    void checkSkipAnim(RecommendBean bean, View view);

}
