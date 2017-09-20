package com.official.read.ui;

import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.RadioGroup;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.util.Toaster;
import com.official.read.weight.RadarView;
import com.official.read.weight.StatisticsView;
import com.official.read.weight.SuperStatisticsView;

import java.util.ArrayList;
import java.util.List;

public class DIYActivity extends BaseActivity {

    RadarView twoView;
    StatisticsView statisticsView;
    RadioGroup group;

    SuperStatisticsView superStatisticsView;

    @Override
    protected int getDisplayState() {
//        return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        return super.getDisplayState();
    }

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
        twoView = (RadarView) $(R.id.diy_twoView);
        twoView.setRadarClickListener(new RadarView.RadarClickListener() {
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

        superStatisticsView = (SuperStatisticsView) $(R.id.diy_superStatisticsView);
        superStatisticsView.setData(getData());

        statisticsView = (StatisticsView) $(R.id.diy_statisticsView);
        statisticsView.setData(getData());
        statisticsView.setStatisticsClickListener(new StatisticsView.StatisticsClickListener() {
            @Override
            public void click(int position, StatisticsView.Statistics statistics) {
                Toaster.makeText("当前查看的是：" + statistics.name + "，份数为：" + statistics.data);
            }
        });
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
        statistics1.data = 500;
        statistics1.name = "java";
        statistics1.percentage = 0.50;
        StatisticsView.Statistics statistics2 = new StatisticsView.Statistics();
        statistics2.data = 300;
        statistics2.name = "C++";
        statistics2.percentage = 0.30;
        StatisticsView.Statistics statistics3 = new StatisticsView.Statistics();
        statistics3.data = 150;
        statistics3.name = "iOS";
        statistics3.percentage = 0.15;
        StatisticsView.Statistics statistics4 = new StatisticsView.Statistics();
        statistics4.data = 50;
        statistics4.name = "C#";
        statistics4.percentage = 0.05;

        StatisticsView.Statistics statistics5 = new StatisticsView.Statistics();
        statistics5.data = 45;
        statistics5.name = "JS";
        statistics5.percentage = 0.04;
        StatisticsView.Statistics statistics6 = new StatisticsView.Statistics();
        statistics6.data = 205;
        statistics6.name = "H5";
        statistics6.percentage = 0.22;
        StatisticsView.Statistics statistics7 = new StatisticsView.Statistics();
        statistics7.data = 145;
        statistics7.name = "PHP";
        statistics7.percentage = 0.06;
        StatisticsView.Statistics statistics8 = new StatisticsView.Statistics();
        statistics8.data = 335;
        statistics8.name = "Ajax";
        statistics8.percentage = 0.14;
        StatisticsView.Statistics statistics9 = new StatisticsView.Statistics();
        statistics9.data = 245;
        statistics9.name = "ASP";
        statistics9.percentage = 0.10;
        StatisticsView.Statistics statistics10 = new StatisticsView.Statistics();
        statistics10.data = 109;
        statistics10.name = "GO";
        statistics10.percentage = 0.04;
        list.add(statistics1);
        list.add(statistics2);
        list.add(statistics3);
        list.add(statistics4);

        list.add(statistics5);
        list.add(statistics6);
        list.add(statistics7);
        list.add(statistics8);
        list.add(statistics9);
        list.add(statistics10);
        return list;
    }
}
