package com.official.read.presenter;

import android.view.View;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.content.listener.MyObserver;
import com.official.read.content.bean.BaseBean;
import com.official.read.content.bean.JusticeBean;
import com.official.read.content.bean.RecommendBean;
import com.official.read.content.http.AES;
import com.official.read.model.JusticeModel;
import com.official.read.model.JusticeModelImpl;
import com.official.read.model.RecommendModel;
import com.official.read.model.RecommendModelImpl;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.view.HistoryView;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class HistoryPresenterImpl extends BasePresenterImpl<HistoryView> implements HistoryPresenter {

    private RecommendModel model;
    private SystemModel themeModel;
    private JusticeModel justiceModel;

    public HistoryPresenterImpl() {
        model = new RecommendModelImpl();
        themeModel = new SystemModelImpl();
        justiceModel = new JusticeModelImpl();
    }

    @Override
    public List<JusticeBean> getJusticeDataByID(int groupButtonID) {
        switch (groupButtonID) {
            case R.id.history_district:
                return justiceModel.getJusticeDistrictData();
            case R.id.history_type:
                return justiceModel.getJusticeTypeData();
            case R.id.history_price:
                return justiceModel.getJusticePriceData();
            case R.id.history_area:
                return justiceModel.getJusticeAreaData();
            default:
                return justiceModel.getJusticeDistrictData();
        }
    }

    @Override
    public void getHistoryData(int city, int building_type, int down_payment, int area, final int page) {
        LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
        mMap.put("type", "3");
        mMap.put("size", Content.PAGE_COUNT + "");
        mMap.put("city", city + "");
        mMap.put("building_type", building_type + "");
        mMap.put("down_payment", down_payment + "");
        mMap.put("area", area + "");
        mMap.put("is_recommend", "0");
        mMap.put("search_name", "");
        mMap.put("page", page + "");
        if (!mMap.isEmpty()) {
            mMap = AES.getSign(mMap);
        } else {
            mMap.put("sign", AES.getSign(""));
        }

        model.initRecommendData(mMap).subscribe(new MyObserver<BaseBean<RecommendBean>>
                (Content.DISPOSABLE_HISTORY_INIT_DATA) {

            @Override
            protected void next(BaseBean<RecommendBean> value) {
                int maxPage = value.data.total_page;
                List<RecommendBean> list = value.data.list;

                if (list != null && list.size() > 0) {
                    if (page == 1 && page == maxPage) { // 只有第一页数据
                        getMvpView().initHistoryData(list);
                        getMvpView().noLoadMoreData();
                    } else if (page == 1) {
                        getMvpView().initHistoryData(list); // 第一页
                    } else {
                        if (page < maxPage) { // 加载更多数据
                            getMvpView().loadMoreData(list);
                        } else { // 已经没有更多数据了
                            getMvpView().noLoadMoreData();
                        }
                    }
                } else { // 没有任何数据
                    getMvpView().noAnywayData();
                }
            }

            @Override
            protected void error(Throwable e) {
                super.error(e);
                getMvpView().error(0, null);
            }
        });
    }

    @Override
    public void checkJusticeBean(JusticeBean bean) {
        int id = bean.id;
        switch (bean.type) {
            case JusticeBean.DISTRICT_TYPE:
                getMvpView().setDistrict(id, bean.name);
                break;
            case JusticeBean.TYPE_TYPE:
                getMvpView().setType(id, bean.name);
                break;
            case JusticeBean.PRICE_TYPE:
                getMvpView().setPrice(id, bean.name);
                break;
            case JusticeBean.AREA_TYPE:
                getMvpView().setArea(id, bean.name);
                break;
        }
    }

    @Override
    public void fuckSetColor(TextView cjMoney) {
        int color = getColorByTheme(themeModel, themeModel.getTheme());
        cjMoney.setTextColor(color);
    }

    @Override
    public void checkSkipAnim(RecommendBean bean, View view) {
        boolean animSet = themeModel.getAnimSet();
        if (animSet) {
            getMvpView().skipNotUseAnim(bean);
        } else {
            getMvpView().skipUseAnim(bean, view);
        }
    }
}
