package com.official.read.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.official.read.R;


/**
 * com.test.zzs.text.base
 * Created by ZP on 2017/4/12.
 * description: 弹窗基类
 * version: 1.0
 */

/**
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                    O\ = /O
 *                ____/`---'\____
 *              .   ' \\| |// `.
 *               / \\||| : |||// \
 *             / _||||| -:- |||||- \
 *               | | \\\ - /// | |
 *             | \_| ''\---/'' | |
 *              \ .-\__ `-` ___/-. /
 *           ___`. .' /--.--\ `. . __
 *        ."" '< `.___\_<|>_/___.' >'"".
 *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *         \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                    `=---='
 *
 * .............................................
 *          佛祖保佑             永无BUG
 */
public abstract class BaseDialog extends AppCompatDialogFragment implements View.OnClickListener,
        OnItemClickListener {

    protected int dialogType = -1;

    protected final static int DIALOG_BOTTOM = 0x0;
    protected final static int DIALOG_TOP = 0x1;
    protected final static int DIALOG_CENTER = 0x2;

    /** 默认的Dialog style */
    private final static int DEFAULT_DIALOG_STYLE = R.style.Dialog_FS;

    protected View rootView;

    protected DialogChildClickListener listener;
    public void setDialogItemClickListener(DialogChildClickListener listener) {
        this.listener = listener;
    }

    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        int type = getDialogType();
        switch (type) {
            case DIALOG_BOTTOM: /** 底部弹出 */
                return new BottomSheetDialog(getContext(), getTheme());
            case DIALOG_CENTER: /** 中心弹出 */
                dialogType = DIALOG_CENTER;
                return new Dialog(getContext(), checkStyle());
            case DIALOG_TOP: /** 顶部弹出 */
                dialogType = DIALOG_TOP;
                return getTopDialog();
            default:
                throw new RuntimeException("Dialog type nonsupport");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutID = getDialogContentView();
        if (layoutID <= 0) {
            throw new NullPointerException("Dialog layout is not null");
        } else {
            rootView = inflater.inflate(layoutID, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initDialogView(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (dialogType == DIALOG_TOP || dialogType == DIALOG_CENTER) {
            setDialog(0, 0);
        }
        setDialogProperty();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            changeToLand();
        } else {
            // 竖屏
            changeToPort();
        }
    }

    /**
     * 横屏调用
     */
    protected void changeToLand() {

    }

    /**
     * 竖屏调用
     */
    protected void changeToPort() {

    }

    /**
     * 设置Dialog的宽高
     *
     * @param width  宽，可为0--->>>为屏幕的 宽度 * 0.7
     * @param height 高，可为0--->>>为 ViewGroup.LayoutParams.WRAP_CONTENT
     */
    protected final void setDialog(int width, int height) {
        if (width == 0) {
            width = (int) (getWidth() * 0.7);
        }
        if (height == 0) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        try {
            getDialog().getWindow().setLayout(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Dialog 弹窗动画
     */
    protected final void setDialogAnimations(int animations) {
        getDialog().getWindow().setWindowAnimations(animations);
    }

    /**
     * 得到Display
     * @return Display
     */
    private Display getDisplay() {
        WindowManager manager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay();
    }

    /**
     * 得到屏幕的宽度
     * @return width
     */
    protected final int getWidth() {
        Point point = new Point();
        getDisplay().getSize(point);
        return point.x;
    }

    /** 获取弹窗的样式 */
    protected int getDialogStyle() {
        return DEFAULT_DIALOG_STYLE;
    }
    /** 获取弹窗的类型 */
    protected abstract int getDialogType();
    /** 获取弹窗的 Layout */
    protected abstract int getDialogContentView();
    /** 初始化弹窗中的 View */
    protected abstract void initDialogView(View view, Bundle bundle);
    /** 设置弹窗的属性 */
    protected abstract void setDialogProperty();
    /** 销毁 */
    protected abstract void destroyAll();

    protected final <T extends View> T $(int id) {
        try {
            return (T) rootView.findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyAll();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private int checkStyle() {
        if (dialogType == DIALOG_BOTTOM) {
            return DEFAULT_DIALOG_STYLE;
        }
        return getDialogStyle() <= 0 ? DEFAULT_DIALOG_STYLE : getDialogStyle();
    }

    private Dialog getTopDialog () {
        Dialog dialog = new Dialog(getContext(), checkStyle());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.TOP);
        }
        return dialog;
    }

    public interface DialogChildClickListener {

        /**
         * 点击
         * @param view  当前视图
         * @param obj   Object
         */
        void dialogClick(View view, Object obj);

    }

}
