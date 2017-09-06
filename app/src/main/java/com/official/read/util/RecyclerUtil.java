package com.official.read.util;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.official.read.R;
import com.official.read.content.Content;

/**
 * com.official.casual.weight.refresh
 * Created by ZP on 2017/7/7.
 * description:
 * version: 1.0
 */

public class RecyclerUtil {

    /**
     * 设置通用的RecyclerView的属性
     * @param context   Context
     * @param lRecyclerView     LRecyclerView
     * @param lRecyclerViewAdapter  LRecyclerViewAdapter
     */
    public static void setLRecyclerViewProperty(Context context, LRecyclerView lRecyclerView, LRecyclerViewAdapter lRecyclerViewAdapter) {
        DividerDecoration divider = new DividerDecoration.Builder(context)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.baseColor)
                .build();
        lRecyclerView.addItemDecoration(divider);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        lRecyclerView.setHeaderViewColor(R.color.baseColor, R.color.baseColor, R.color.white);
        lRecyclerView.setFooterViewColor(R.color.baseColor, R.color.baseColor, R.color.white);

        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);

    }

    /**
     * 根据主题切换设置相对应的颜色
     */
    public static void setSwipeLuRecyclerViewTheme(LuRecyclerView luRecyclerView, SwipeRefreshLayout refreshLayout) {
        String theme = (String) SPUtil.get(SPUtil.BASE_COLOR, Content.THEME_RED);
        int color = Content.getBaseColorByTheme(theme);
        if (refreshLayout != null) {
            refreshLayout.setColorSchemeResources(color);
        }
        if (luRecyclerView != null) {
            luRecyclerView.setFooterViewColor(color, color, R.color.white);
        }
    }

    /**
     * 设置SwipeRefreshLayout 与 LuRecyclerView的属性
     */
    public static void setSwipeLuRecyclerViewProperty(LuRecyclerView luRecyclerView, SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(R.color.baseColor);
        luRecyclerView.setFooterViewColor(R.color.baseColor, R.color.baseColor, R.color.white);
        luRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        //设置底部加载文字提示
        luRecyclerView.setFooterViewHint("拼命加载中","已经全部为你呈现了","网络不给力啊，点击再试一次吧");
    }

    /**
     * 设置luRecyclerView的分割线，显示方式
     */
    public static void setLuRecyclerViewDisplay(Context context, LuRecyclerViewAdapter luRecyclerViewAdapter, LuRecyclerView luRecyclerView) {
        luRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        LuDividerDecoration divider = new LuDividerDecoration.Builder(context, luRecyclerViewAdapter)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
//                .setColorResource(R.color.baseColor)
                .setColorResource(android.R.color.transparent)
                .build();
        luRecyclerView.addItemDecoration(divider);
    }

}
