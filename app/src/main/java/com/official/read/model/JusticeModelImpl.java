package com.official.read.model;

import com.official.read.R;
import com.official.read.content.bean.JusticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/2.
 * description:
 * version: 1.0
 */

public class JusticeModelImpl implements JusticeModel {

    @Override
    public List<JusticeBean> getJusticeDistrictData() {
        int type = JusticeBean.DISTRICT_TYPE;
        List<JusticeBean> list = new ArrayList<>();
        JusticeBean b0 = new JusticeBean(0, "所有区域", true, R.color.baseColor, type);
        JusticeBean b1 = new JusticeBean(2830, "浦东新区", false, R.color.black, type);
        JusticeBean b2 = new JusticeBean(78, "黄埔区", false, R.color.black, type);
        JusticeBean b3 = new JusticeBean(2813, "徐汇区", false, R.color.black, type);
        JusticeBean b4 = new JusticeBean(2815, "长宁区", false, R.color.black, type);
        JusticeBean b5 = new JusticeBean(2817, "静安区", false, R.color.black, type);
        JusticeBean b6 = new JusticeBean(2841, "普陀区", false, R.color.black, type);
        JusticeBean b7 = new JusticeBean(2822, "虹口区", false, R.color.black, type);
        JusticeBean b8 = new JusticeBean(2823, "杨浦区", false, R.color.black, type);
        JusticeBean b9 = new JusticeBean(2825, "闵行区", false, R.color.black, type);
        JusticeBean b10 = new JusticeBean(2824, "宝山区", false, R.color.black, type);
        JusticeBean b11 = new JusticeBean(2826, "嘉定区", false, R.color.black, type);
        JusticeBean b12 = new JusticeBean(2834, "松江区", false, R.color.black, type);
        JusticeBean b13 = new JusticeBean(2833, "青浦区", false, R.color.black, type);
        JusticeBean b14 = new JusticeBean(2837, "奉贤区", false, R.color.black, type);
        list.add(b0);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);
        list.add(b7);
        list.add(b8);
        list.add(b9);
        list.add(b10);
        list.add(b11);
        list.add(b12);
        list.add(b13);
        list.add(b14);
        return list;
    }

    @Override
    public List<JusticeBean> getJusticeTypeData() {
        int type = JusticeBean.TYPE_TYPE;
        List<JusticeBean> list = new ArrayList<>();
        JusticeBean b0 = new JusticeBean(0, "所有类型", true, R.color.baseColor, type);
        JusticeBean b1 = new JusticeBean(1, "商铺", false, R.color.black, type);
        JusticeBean b2 = new JusticeBean(2, "工业", false, R.color.black, type);
        JusticeBean b3 = new JusticeBean(3, "住宅", false, R.color.black, type);
        JusticeBean b4 = new JusticeBean(4, "别墅", false, R.color.black, type);
        JusticeBean b5 = new JusticeBean(5, "办公", false, R.color.black, type);
        list.add(b0);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        return list;
    }

    @Override
    public List<JusticeBean> getJusticePriceData() {
        int type = JusticeBean.PRICE_TYPE;
        List<JusticeBean> list = new ArrayList<>();
        JusticeBean b0 = new JusticeBean(0, "所有价格", true, R.color.baseColor, type);
        JusticeBean b1 = new JusticeBean(1, "300-500万", false, R.color.black, type);
        JusticeBean b2 = new JusticeBean(2, "500-1000万", false, R.color.black, type);
        JusticeBean b3 = new JusticeBean(3, "1000万以上", false, R.color.black, type);
        list.add(b0);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        return list;
    }

    @Override
    public List<JusticeBean> getJusticeAreaData() {
        int type = JusticeBean.AREA_TYPE;
        List<JusticeBean> list = new ArrayList<>();
        JusticeBean b0 = new JusticeBean(0, "所有面积", true, R.color.baseColor, type);
        JusticeBean b1 = new JusticeBean(1, "50㎡--100㎡", false, R.color.black, type);
        JusticeBean b2 = new JusticeBean(2, "100㎡--200㎡", false, R.color.black, type);
        JusticeBean b3 = new JusticeBean(3, "200㎡--300㎡", false, R.color.black, type);
        JusticeBean b4 = new JusticeBean(4, "300㎡以上", false, R.color.black, type);
        list.add(b0);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        return list;
    }

    @Override
    public List<JusticeBean> getJusticeStateData() {
        int type = JusticeBean.STATE_TYPE;
        List<JusticeBean> list = new ArrayList<>();
        JusticeBean b0 = new JusticeBean(0, "所有状态", true, R.color.baseColor, type);
        JusticeBean b1 = new JusticeBean(1, "缓拍", false, R.color.black, type);
        JusticeBean b2 = new JusticeBean(2, "流标", false, R.color.black, type);
        JusticeBean b3 = new JusticeBean(3, "撤拍", false, R.color.black, type);
        JusticeBean b4 = new JusticeBean(4, "正在拍卖", false, R.color.black, type);
        JusticeBean b5 = new JusticeBean(5, "即将开始", false, R.color.black, type);
        JusticeBean b6 = new JusticeBean(6, "已成交", false, R.color.black, type);
        list.add(b0);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);
        return list;
    }
}
