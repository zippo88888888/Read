package com.official.read.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.content.Content;
import com.official.read.content.ReadException;
import com.official.read.util.L;
import com.official.read.util.RecyclerUtil;
import com.official.read.util.Toaster;
import com.official.read.util.anim.EasyTransition;
import com.official.read.util.anim.EasyTransitionOptions;
import com.official.themelibrary.base.SkinBaseFragment;

import java.io.Serializable;


/**
 * com.official.read.base
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public abstract class BaseFragment<P extends BasePresenter<V>,V extends BaseView> extends /*Fragment*/SkinBaseFragment implements
        View.OnClickListener, BaseView, BaseDialog.DialogChildClickListener, OnNetWorkErrorListener,
        SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, OnRefreshListener, OnItemClickListener,
        LuRecyclerView.LScrollListener {

    protected View rootView;
    private Dialog dialog;
    protected P presenter;
    protected FragmentActivity fragmentActivity;

    private TelActivityListener telActivityListener;

    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次加载数据
    protected boolean isFirst;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.fragmentActivity = getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst && isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见--->>>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutID = getContentView();
        if (layoutID <= 0) {
            throw new NullPointerException("Fragment layout is not null");
        } else {
//            if (rootView == null) {
//                rootView = inflater.inflate(layoutID, container, false);
//                presenter = initPresenter();
//                if (presenter != null) {
//                    presenter.attachView((V) this);
//                }
//                initView();
//                initData();
//            } else {
//                L.e("加载完毕");
//            }
            if (rootView == null) {
                rootView = inflater.inflate(layoutID, container, false);
                presenter = initPresenter();
                if (presenter != null) {
                    presenter.attachView((V) this);
                }
                initView();
//                initData();
                //可见，但是并没有加载过
                if (isFragmentVisible && !isFirst) {
                    onFragmentVisibleChange(true);
                }
            }
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerUtil.setSwipeLuRecyclerViewTheme(getLuRecyclerView(), getSwipeRefreshLayout());

    }

    @Override
    public void onDestroyView() {
//        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDetach();
    }

    protected abstract int getContentView();
    protected abstract P initPresenter();
    protected abstract void initView();
    // 懒加载实现
    protected abstract void initData();

    // 配合更换主题实现相对应的颜色
    protected abstract SwipeRefreshLayout getSwipeRefreshLayout();
    protected abstract LuRecyclerView getLuRecyclerView();

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    private void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            initData();
        } else {
            onFragmentVisible();
        }
    }

    /**
     * Fragment已变为不可见状态
     */
    protected void onFragmentVisible() {
        // do something
    }

    protected final <T extends View> T $(int id) {
        try {
            return (T) rootView.findViewById(id);
        } catch (ClassCastException e) {
            L.e("Could not cast View to concrete class");
            throw e;
        }
    }

    protected void jumpActivity(Class clazz) {
        startActivity(new Intent(fragmentActivity, clazz));
    }

    protected void jumpActivity(ArrayMap<String, Object> map, Class clazz) {
        jumpActivity(map, clazz, new View[0]);
    }

    protected void jumpActivity(ArrayMap<String, Object> map, Class clazz, View... views) {
        if (map.isEmpty()) {
            throw new NullPointerException("ArrayMap<String, Object> map is not null");
        }
        boolean isAnim = false;
        if (views != null && views.length > 0) {
            isAnim = true;
        }
        Intent intent = new Intent(fragmentActivity, clazz);
        Bundle bundle = new Bundle();
        for (String key : map.keySet()) {
            Object o = map.get(key);
            if (o instanceof String) {
                // int,double,float,String
                bundle.putString(key, (String) o);
            } else if (o instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) o);
            } else if (o instanceof Parcelable) {
                bundle.putParcelable(key, (Parcelable) o);
            } else {
                throw new ReadException("type error");
            }
        }
        intent.putExtras(bundle);
        if (isAnim) {
            EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(fragmentActivity, views);
            EasyTransition.startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void dialogClick(View view, Object obj) {

    }

    /**
     * SwipeRefreshLayout 与 RecyclerView 的下拉刷新
     */
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    /**
     * RecyclerView 的item的点击事件
     */
    @Override
    public void onItemClick(View view, int position) {

    }

    /**
     * RecyclerView 加载更多失败会调用
     */
    @Override
    public void reload() {

    }

    private Dialog createDialog() {
        ProgressDialog dialog = new ProgressDialog(fragmentActivity);
        dialog.setMessage("请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.getWindow().setWindowAnimations(android.R.style.Animation_Translucent);
        return dialog;
    }

    @Override
    public void showDialog(Boolean cancelable) {
        if (dialog == null) {
            dialog = createDialog();
        }
        dialog.setCanceledOnTouchOutside(false);
        if (cancelable != null) {
            dialog.setCancelable(cancelable);
        }

        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public Context getBaseViewContext() {
        return fragmentActivity.getApplicationContext();
    }

    @Override
    public void noAnywayData() {
        Toaster.makeText("没有数据");
    }

    @Override
    public void noLoadMoreData() {
        Toaster.makeText("没有更多数据了");
    }

    @Override
    public void error(int code, ReadException e) {
        Toaster.makeText("网络异常");
    }

    @Override
    public void setMessage(String message, ReadException e) {
        if (message != null) {
            Toaster.makeText(message);
        }
        if (!Content.IS_OFFICIAL && e != null) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollUp() {

    }

    @Override
    public void onScrollDown() {

    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {

    }

    @Override
    public void onScrollStateChanged(int state) {

    }

    public TelActivityListener getTelActivityListener() {
        if (telActivityListener == null) {
            fragmentActivity.finish();
            return new TelActivityListener() {
                @Override
                public void telActivity(Fragment fragment, Object value) {
                    Toaster.makeText("开发人员调试阶段");
                }
            };
        } else {
            return telActivityListener;
        }
    }

    public void setTelActivityListener(TelActivityListener telActivityListener) {
        this.telActivityListener = telActivityListener;
    }

    /**
     * 与Activity交互
     */
    public interface TelActivityListener {

        void telActivity(Fragment fragment, Object value);
    }
}
