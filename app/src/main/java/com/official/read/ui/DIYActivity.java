package com.official.read.ui;

import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.util.L;
import com.official.read.util.Toaster;
import com.official.read.weight.StatisticsView;
import com.official.read.weight.TwoView;

import java.util.ArrayList;
import java.util.List;

public class DIYActivity extends BaseActivity {

    TwoView twoView;
    StatisticsView statisticsView;
    RadioGroup group;

    @Override
    protected int getContentView() {
        return R.layout.activity_diy;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        setTitle("TEST-DIY-PAGE");
        twoView = (TwoView) $(R.id.diy_twoView);
        twoView.setRadarClickListener(new TwoView.RadarClickListener() {
            @Override
            public void start() {
                Toaster.makeText("雷达开始工作");
            }

            @Override
            public void stop() {
                Toaster.makeText("雷达停止工作");
            }
        });

        group = (RadioGroup) $(R.id.diy_statisticsGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.diy_1:
                        statisticsView.setRectangleShow();
                        break;
                    case R.id.diy_2:
                        statisticsView.setDiscountShow();
                        break;
                }
            }
        });


        statisticsView = (StatisticsView) $(R.id.diy_statisticsView);
        statisticsView.setData(getData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        twoView.clear();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected LuRecyclerView getLuRecyclerView() {
        return null;
    }

    private List<StatisticsView.Statistics> getData() {
        List<StatisticsView.Statistics> list = new ArrayList<>();
        StatisticsView.Statistics statistics1 = new StatisticsView.Statistics();
        statistics1.data = 400;
        statistics1.name = "java";
        StatisticsView.Statistics statistics2 = new StatisticsView.Statistics();
        statistics2.data = 80;
        statistics2.name = "C++";
        StatisticsView.Statistics statistics3 = new StatisticsView.Statistics();
        statistics3.data = 60;
        statistics3.name = "iOS";
        StatisticsView.Statistics statistics4 = new StatisticsView.Statistics();
        statistics4.data = 210;
        statistics4.name = "C#";
        StatisticsView.Statistics statistics5 = new StatisticsView.Statistics();
        statistics5.data = 45;
        statistics5.name = "JS";
        StatisticsView.Statistics statistics6 = new StatisticsView.Statistics();
        statistics6.data = 205;
        statistics6.name = "H5";
        StatisticsView.Statistics statistics7 = new StatisticsView.Statistics();
        statistics7.data = 145;
        statistics7.name = "PHP";
        StatisticsView.Statistics statistics8 = new StatisticsView.Statistics();
        statistics8.data = 335;
        statistics8.name = "Ajax";
        StatisticsView.Statistics statistics9 = new StatisticsView.Statistics();
        statistics9.data = 245;
        statistics9.name = "ASP";
        StatisticsView.Statistics statistics10 = new StatisticsView.Statistics();
        statistics10.data = 109;
        statistics10.name = "GO";
        list.add(statistics2);
        list.add(statistics3);
        list.add(statistics4);
        list.add(statistics5);
        list.add(statistics1);
        list.add(statistics6);
        list.add(statistics7);
        list.add(statistics8);
        list.add(statistics9);
        list.add(statistics10);
        return list;
    }
}
