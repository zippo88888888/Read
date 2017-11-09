package com.official.read.presenter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.base.BaseViewHolder;
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
import com.official.read.util.ImageLoader;
import com.official.read.util.StringUtil;
import com.official.read.view.JusticeView;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/2.
 * description:
 * version: 1.0
 */

public class JusticePresenterImpl extends BasePresenterImpl<JusticeView> implements JusticePresenter {

    private JusticeModel model;
    private RecommendModel recommendModel;
    private SystemModel themeModel;

    public JusticePresenterImpl() {
        model = new JusticeModelImpl();
        recommendModel = new RecommendModelImpl();
        themeModel = new SystemModelImpl();
    }

    @Override
    public List<JusticeBean> getJusticeDataByID(int groupButtonID) {
        switch (groupButtonID) {
            case R.id.justice_district:
                return model.getJusticeDistrictData();
            case R.id.justice_type:
                return model.getJusticeTypeData();
            case R.id.justice_price:
                return model.getJusticePriceData();
            case R.id.justice_area:
                return model.getJusticeAreaData();
            case R.id.justice_state:
                return model.getJusticeStateData();
            default:
                return model.getJusticeDistrictData();
        }
    }

    @Override
    public void initJusticeData(int city, int building_type, int down_payment, int area, int status, final int page) {
        LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
        mMap.put("type", "1");
        mMap.put("size", Content.PAGE_COUNT + "");
        mMap.put("city", city + "");
        mMap.put("building_type", building_type + "");
        mMap.put("down_payment", down_payment + "");
        mMap.put("area", area + "");
        mMap.put("status", status + "");
        mMap.put("is_recommend", "0");
        mMap.put("search_name", "");
        mMap.put("page", page + "");
        if (!mMap.isEmpty()) {
            mMap = AES.getSign(mMap);
        } else {
            mMap.put("sign", AES.getSign(""));
        }
        recommendModel.initRecommendData(mMap).subscribe(new MyObserver<BaseBean<RecommendBean>>
                (Content.DISPOSABLE_JUSTICE_INIT_DATA) {

            @Override
            protected void next(BaseBean<RecommendBean> value) {
                int maxPage = value.data.total_page;
                List<RecommendBean> list = value.data.list;
                if (list != null && list.size() > 0) {
                    if (page == 1 && page == maxPage) {
                        if (isAttachView()) getMvpView().initJusticeData(list);
                        if (isAttachView()) getMvpView().noLoadMoreData();
                    } else if (page == 1){
                        if (isAttachView()) getMvpView().initJusticeData(list); // 第一页
                    } else {
                        if (page < maxPage) {
                            if (isAttachView()) getMvpView().loadMore(list);
                        } else { // 已经没有数据了
                            if (isAttachView()) getMvpView().noLoadMoreData();
                        }
                    }
                } else {
                    if (isAttachView()) getMvpView().noAnywayData();
                }
            }

            @Override
            protected void error(Throwable e) {
                super.error(e);
                if (isAttachView()) getMvpView().error(0, null);
            }
        });
    }

    @Override
    public void checkJusticeBean(JusticeBean bean) {
        int id = bean.id;
        switch (bean.type) {
            case JusticeBean.DISTRICT_TYPE:
                if (isAttachView()) getMvpView().setDistrict(id, bean.name);
                break;
            case JusticeBean.TYPE_TYPE:
                if (isAttachView()) getMvpView().setType(id, bean.name);
                break;
            case JusticeBean.PRICE_TYPE:
                if (isAttachView()) getMvpView().setPrice(id, bean.name);
                break;
            case JusticeBean.AREA_TYPE:
                if (isAttachView()) getMvpView().setArea(id, bean.name);
                break;
            case JusticeBean.STATE_TYPE:
                if (isAttachView()) getMvpView().setState(id, bean.name);
                break;
        }
    }

    @Override
    public void checkRecommendState(BaseViewHolder holder, RecommendBean item, int listSize, int position) {
        int color = getColorByTheme(themeModel, themeModel.getTheme());
        ImageView pic = holder.getView(R.id.new_home_list_item_pic); // 图片
        TextView title = holder.getView(R.id.new_home_list_item_title); // 标题
        TextView state = holder.getView(R.id.new_home_list_item_state); // 状态
        TextView cjTxt = holder.getView(R.id.new_home_list_item_cjMoneyTxt); // 成交价的文字说明
        TextView cjMoney = holder.getView(R.id.new_home_list_item_cjMoney); // 成交价
        TextView pgTxt = holder.getView(R.id.new_home_list_item_pgMoneyTxt); // 评估价的文字说明
        TextView pgMoney = holder.getView(R.id.new_home_list_item_pgMoney); // 评估价
        TextView time = holder.getView(R.id.new_home_list_item_time); // 时间
        ImageView timePic = holder.getView(R.id.new_home_list_item_timePic); // 时间的图片
        TextView people = holder.getView(R.id.new_home_list_item_people); // 围观人数 参拍人数
        TextView submit = holder.getView(R.id.new_home_list_item_submit); // 我要参拍
        View bg = holder.getView(R.id.new_home_list_item_bg);

        title.setText(item.house_name);
        people.setText(item.browse + "人围观/" + item.participants_number + "人报名");
        ImageLoader.loadImage(pic, item.img);

        cjMoney.setTextColor(color);
        pgMoney.setTextColor(color);

        // 如果评估价不为空就为评估价     否则就为市场参考价（针对所有状态）
        String pp = item.price;
        if (pp != null && !"".equals(pp) && !"0".equals(pp)) {
            pgTxt.setText("评估价");
            pgMoney.setText(StringUtil.moneyFor00orMoney(item.price));
        } else {
            pgTxt.setText("参考价");
            if (item.reserve_price != null || "".equals(item.reserve_price)) {
                pgMoney.setText(StringUtil.moneyFor00orMoney(item.reserve_price));
            } else {
                pgMoney.setText("--");
            }
        }
//        if (position == listSize - 1) {
//            bg.setVisibility(View.GONE);
//        } else {
//            bg.setVisibility(View.VISIBLE);
//        }
        if (item.auction_status == null || "".equals(item.auction_status)) {
            cjTxt.setText("起拍价");
            cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
            state.setText("已结束");
            time.setText("已结束");
            time.setTextColor(Color.parseColor("#999999"));
//            timePic.setImageResource(R.drawable.home_ic_no);
            submit.setText("查看详情");
            return;
        }
        int status = Integer.parseInt(item.auction_status);
        switch (status) {
            case RecommendBean.HOUSE_STATUS_ABOUNT_TO_BEGIN: { // 即将开始
                cjTxt.setText("起拍价");
                cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
                submit.setText("我要报名");

                state.setText("即将开始");
//                state.setTextColor(Color.parseColor("#ffa500"));
                state.setTextColor(color);
                time.setText("开始时间" + item.end_times);
                time.setTextColor(Color.parseColor("#999999"));
//                timePic.setImageResource(R.drawable.home_ic_no);
                break;
            }
            case RecommendBean.HOUSE_STATUS_STARTING: { // 正在拍卖
                cjTxt.setText("起拍价");
                cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
                submit.setText("我要报名");

                state.setText("正在拍卖");
                state.setTextColor(color);
//                state.setTextColor(Color.parseColor("#e41f05"));
                // 倒计时
//                startCountDown(item, time, position);
                time.setText("正在拍卖");
//                timePic.setImageResource(R.drawable.home_ic_no);
//                timePic.setImageResource(R.drawable.home_ic_time);
                break;
            }
            case RecommendBean.HOUSE_STATUS_KNOCKDOWN: { // 已成交
                String price = item.transaction_price;
                if (price == null || "".equals(price) || "0".equals(price)) {
                    cjTxt.setText("起拍价");
                    cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
                } else {
                    cjTxt.setText("成交价");
                    cjMoney.setText(StringUtil.moneyFor00orMoney(item.transaction_price));
                }
                submit.setText("查看详情");

                state.setText("已成交");
                state.setTextColor(Color.parseColor("#999999"));
                time.setText("已结束");
                time.setTextColor(Color.parseColor("#999999"));
//                timePic.setImageResource(R.drawable.home_ic_no);
                break;
            }
            case RecommendBean.HOUSE_STATUS_DOWNTEMPO: { // 缓拍
                cjTxt.setText("起拍价");
                cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
                submit.setText("我要报名");

                state.setText("缓拍");
//                state.setTextColor(Color.parseColor("#e41f05"));
                state.setTextColor(color);
//                timePic.setImageResource(R.drawable.home_ic_no);
//                timePic.setImageResource(R.drawable.home_ic_time);
                time.setText("已结束");
                break;
            }
            case RecommendBean.HOUSE_STATUS_ABORTIVE_AUCTION: { // 流标
                cjTxt.setText("起拍价");
                cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
                submit.setText("查看详情");

                state.setText("流标");
                state.setTextColor(Color.parseColor("#999999"));
                time.setText("已结束");
                time.setTextColor(Color.parseColor("#999999"));
//                timePic.setImageResource(R.drawable.home_ic_no);
                break;
            }
            case RecommendBean.HOUSE_STATUS_BACKOUT: { // 撤拍
                cjTxt.setText("起拍价");
                cjMoney.setText(StringUtil.moneyFor00orMoney(item.down_payment));
                submit.setText("查看详情");

                state.setText("撤拍");
                state.setTextColor(Color.parseColor("#999999"));
                time.setText("已结束");
                time.setTextColor(Color.parseColor("#999999"));
//                timePic.setImageResource(R.drawable.home_ic_no);
                break;
            }
        }
    }

    @Override
    public void checkSkipAnim(RecommendBean bean, View view) {
        boolean animSet = themeModel.getAnimSet();
        if (animSet) {
            if (isAttachView()) getMvpView().skipNotUseAnim(bean);
        } else {
            if (isAttachView()) getMvpView().skipUseAnim(bean, view);
        }
    }
}
