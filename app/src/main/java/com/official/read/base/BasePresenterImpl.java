package com.official.read.base;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.content.ReadException;
import com.official.read.content.bean.BaseBean;
import com.official.read.model.ThemeModel;
import com.official.read.model.ThemeModelImpl;
import com.official.read.util.L;
import com.official.read.util.NetworkUtil;
import com.official.read.util.Toaster;

import java.util.List;

/**
 * com.official.read.base
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private final static String ERROR_MESSAGE = Toaster.getContext().getResources().getString(R.string.mvpView_message);

    private V mvpView;

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    /**
     * 判断 view是否为空
     */
    private boolean isAttachView() {
        return mvpView != null;
    }

    /**
     * 返回目标view
     */
    public V getMvpView() {
        checkViewAttach();
        return mvpView;
    }

    /**
     * 检查view和presenter是否连接
     */
    protected void checkViewAttach() {
        if (!isAttachView()) {
//            throw new ReadException("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
            L.e(ERROR_MESSAGE);
            return;
        }
    }

    /**
     * 根据主题获取相对应的颜色
     */
    protected int getColorByTheme(ThemeModel model, String theme) {
        if (Content.THEME_RED.equals(theme)) {
            return model.getThemeRed();
        } else if (Content.THEME_PINK.equals(theme)) {
            return model.getThemePink();
        } else if (Content.THEME_VIOLET.equals(theme)) {
            return model.getThemeViolet();
        } else if (Content.THEME_BLUE.equals(theme)) {
            return model.getThemeBlue();
        } else if (Content.THEME_GREEN.equals(theme)) {
            return model.getThemeGreen();
        } else if (Content.THEME_BLACK.equals(theme)) {
            return model.getThemeBlack();
        } else {
            return model.getThemeRed();
        }
    }

    protected void checkNetworkState() {
        boolean connected = NetworkUtil.isNetworkConnected(getMvpView().getBaseViewContext());
        if (!connected) {
            Toaster.makeText("无网络连接");
            return;
        }
    }
}
