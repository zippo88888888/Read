package com.official.read.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.jaeger.library.StatusBarUtil;
import com.official.read.R;
import com.official.read.adapter.SearchHistoryAdapter;
import com.official.read.base.BaseFragment;
import com.official.read.content.Content;
import com.official.read.content.bean.SearchHistoryBean;
import com.official.read.content.listener.MyAnimatorListener;
import com.official.read.presenter.SearchPresenterImpl;
import com.official.read.util.AndroidUtil;
import com.official.read.util.MathUtil;
import com.official.read.util.RecyclerUtil;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;
import com.official.read.util.anim.AnimUtil;
import com.official.read.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class SearchFragment extends BaseFragment<SearchPresenterImpl,SearchView> implements SearchView,
        ViewTreeObserver.OnPreDrawListener, TextView.OnEditorActionListener, Runnable {

    LinearLayout rootLayout;
    LinearLayout contentLayout;
    LinearLayout searchLayout;

    EditText searchEdit;
    SuperTextView searchPic;
    LinearLayout itemLayout;

    View search_bgLayout;

    private int centerX;
    private int centerY;

    LuRecyclerView recyclerView;
    SearchHistoryAdapter adapter;
    List<SearchHistoryBean> list = new ArrayList<>();

    TextView clear;

    SuperTextView textView1;
    SuperTextView textView2;
    SuperTextView textView3;
    SuperTextView textView4;

    @Override
    protected int getContentView() {
        return R.layout.fragment_search;
    }

    @Override
    protected SearchPresenterImpl initPresenter() {
        return new SearchPresenterImpl();
    }

    @Override
    protected void initView() {
        rootLayout = $(R.id.searchRoot);
        contentLayout = $(R.id.search_ContentLayout);
        searchLayout = $(R.id.search_edit_layout);

        searchEdit = $(R.id.edit_search);
        searchPic = $(R.id.img_search);
        itemLayout = $(R.id.search_itemsLayout);
        search_bgLayout = $(R.id.search_bgLayout);

        recyclerView = $(R.id.search_RecyclerView);
        adapter = new SearchHistoryAdapter(fragmentActivity, list);
        LuRecyclerViewAdapter lRecyclerViewAdapter = new LuRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        RecyclerUtil.setLuRecyclerViewDisplay(fragmentActivity, lRecyclerViewAdapter, recyclerView);
        recyclerView.setRefreshing(false);
        recyclerView.setLoadMoreEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(this);
        clear = $(R.id.clear_history);

        searchPic.setOnClickListener(this);
        itemLayout.setOnClickListener(this);
        search_bgLayout.setOnClickListener(this);
        clear.setOnClickListener(this);

        textView1 = $(R.id.search1);
        textView2 = $(R.id.search2);
        textView3 = $(R.id.search3);
        textView4 = $(R.id.search4);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);

        dynamicAddView(searchPic, "superTextViewAttr", R.color.baseColor);
        dynamicAddView(textView1, "superTextViewAttr", R.color.baseColor);
        dynamicAddView(textView2, "superTextViewAttr", R.color.baseColor);
        dynamicAddView(textView3, "superTextViewAttr", R.color.baseColor);
        dynamicAddView(textView4, "superTextViewAttr", R.color.baseColor);
        searchLayout.getViewTreeObserver().addOnPreDrawListener(this);
        searchEdit.setOnEditorActionListener(this);
        presenter.getSearchHistory();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected LuRecyclerView getLuRecyclerView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search_bgLayout:
                AndroidUtil.closeKeyboard(fragmentActivity);
                onBackPressed();
                break;
            case R.id.img_search:
                presenter.submitSearch(searchEdit.getText().toString(), true);
                break;
            case R.id.search1:
                presenter.submitSearch(Content.HOT_HP, false);
                break;
            case R.id.search2:
                presenter.submitSearch(Content.HOT_JA, false);
                break;
            case R.id.search3:
                presenter.submitSearch(Content.HOT_PD, false);
                break;
            case R.id.search4:
                presenter.submitSearch(Content.HOT_XH, false);
                break;
            case R.id.clear_history:
                presenter.clearHistory();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SearchHistoryBean item = adapter.getItem(position);
        searchEdit.setText(item.name);
        searchEdit.setSelection(0, searchEdit.getText().toString().length());
        searchEdit.setSelection(0);
        searchEdit.setSelectAllOnFocus(true);
        presenter.submitSearch(item.name, false);
    }

    @Override
    public void initSearchHistoryData(List<SearchHistoryBean> list) {
        adapter.setDataList(list);
    }

    @Override
    public void notSearchHistoryData() {
        adapter.clear();
        Toaster.makeText("暂无搜索记录");
    }

    @Override
    public void skipActivity(String history) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("history", history);
        jumpActivity(map, ListActivity.class);
    }

    @Override
    public void setStatusBarTransparent() {
        int color = Content.getBaseColorByTheme(presenter.getTheme());
        int statusColor = fragmentActivity.getResources().getColor(color);
        StatusBarUtil.setColor(fragmentActivity, statusColor, 105);
    }

    @Override
    public void setStatusBarNormal() {
        int color = Content.getBaseColorByTheme(presenter.getTheme());
        int statusColor = fragmentActivity.getResources().getColor(color);
        StatusBarUtil.setColor(fragmentActivity, statusColor, 0);
    }

    @Override
    public boolean onPreDraw() {
        searchLayout.getViewTreeObserver().removeOnPreDrawListener(this);
        itemLayout.setVisibility(View.INVISIBLE);

        searchLayout.setVisibility(View.INVISIBLE);

        centerX = searchPic.getLeft() + searchPic.getWidth() / 2;
        centerY = searchPic.getTop() + searchPic.getHeight() / 2;

        // 执行动画的对象，必须是RevealLinearLayout或者RevealLinearLayout父类
        // 动画中心X点坐标
        // 动画中心Y点坐标
        // 动画开始时的圆半径
        // 动画结束时的圆半径
        float endR = MathUtil.hypo(searchLayout.getWidth(), searchLayout.getHeight());
        SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(searchLayout,
                centerX, centerY, 20, endR);
        mRevealAnimator.addListener(new MyAnimatorListener() {
            @Override
            public void start() {
                searchLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void end() {
                AnimUtil.setStartAnimForSearch(itemLayout);
                itemLayout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(SearchFragment.this, 150);
            }
        });
        mRevealAnimator.setDuration(200);
        // 延时100毫秒执行动画
        mRevealAnimator.setStartDelay(100);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();
        return true;
    }

    public boolean onBackPressed() {
        SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(contentLayout,
                centerX, centerY, 20, MathUtil.hypo(contentLayout.getWidth(), contentLayout.getHeight()));
        mRevealAnimator = mRevealAnimator.reverse();
        if (mRevealAnimator == null) {
            return false;
        }
        mRevealAnimator.addListener(new MyAnimatorListener() {
            @Override
            public void start() {
                contentLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void end() {
                contentLayout.setVisibility(View.INVISIBLE);
                fragmentActivity.getSupportFragmentManager().popBackStack();

                presenter.setStatusBarState(Content.STATUE_NORMAL);
            }
        });
        mRevealAnimator.setDuration(200);
        mRevealAnimator.setStartDelay(100);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            presenter.submitSearch(searchEdit.getText().toString(), true);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        searchEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchEdit, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setStatusBarState(Content.STATUE_TRANSPARENT);
    }
}
