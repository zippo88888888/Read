package com.official.read.view;

import com.official.read.base.BaseView;
import com.official.read.content.bean.DetailBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface DetailView extends BaseView {

    void initDetailData(DetailBean bean);

    void tel();

    /**
     * 展开
     */
    void show();

    /**
     * 隐藏
     */
    void hidden();

    /**
     * 设置亮点数据
     */
    void setLightGridViewData(List<String> data);

    /**
     * 隐藏亮点GridView
     */
    void hiddenLightGridView();

    /**
     * 赋值
     * @param deposit   保证金
     * @param source    房屋类型
     * @param priority  优先购买人
     */
    void setTop3Value(String deposit, String source, String priority);

    /**
     * 设置值
     */
    void setValue(String bidPrice, String bidPriceT, String qpPrice, String qpPriceT, String houseTime);

    /**
     * 设置收藏状态
     */
    void setConnectionState(String state);

    void showBottomLayout();

    void hiddenBottomLayout();

    void firstUseAnim();

    void firstNotUseAnim();

    void useAnim();

    void notUseAnim();
}
