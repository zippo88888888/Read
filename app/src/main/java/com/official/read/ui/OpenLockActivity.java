package com.official.read.ui;

import android.app.Service;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.jaeger.library.StatusBarUtil;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.content.Content;
import com.official.read.presenter.LockPresenterImpl;
import com.official.read.util.anim.AnimUtil;
import com.official.read.view.LockView;
import com.official.read.weight.MyKeyBoardView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class OpenLockActivity extends BaseActivity<LockPresenterImpl,LockView> implements LockView, MyKeyBoardView.InputTxtListener {

    private ImageView bg;

    private KeyboardView keyboardView;
    private MyKeyBoardView myKeyBoardView;

    private TextView msgTxt;

    private Vibrator vibrator;

    @Override
    protected int getContentView() {
        isSetBar = false;
        return R.layout.activity_open_lock;
    }

    @Override
    protected LockPresenterImpl initPresenter() {
        return new LockPresenterImpl();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(this, 255);
        bg = $(R.id.open_lock_bg);
        keyboardView = $(R.id.open_lock_keyBoard);
        myKeyBoardView = $(R.id.open_lock_myKeyBoardView);
        msgTxt = $(R.id.open_lock_showMsg);
        myKeyBoardView.setInputTxtListener(this);
//        dynamicAddView(myKeyBoardView, "myKeyBoardViewAttr", R.color.baseColor);



    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myKeyBoardView.setKeyBoardView(keyboardView);
        myKeyBoardView.showKeyBoard(false);
        myKeyBoardView.isOpenLock(true);
        vibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
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

    @Override
    public void onBackPressed() {
        boolean keyBoardState = myKeyBoardView.getKeyBoardState();
        if (keyBoardState) {
            myKeyBoardView.hiddenKeyBoard(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void back() {
        myKeyBoardView.hiddenKeyBoard(false);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
    }

    @Override
    public int getLockLength() {
        return myKeyBoardView.getLength();
    }

    @Override
    public void setLockSuccess() {

    }

    @Override
    public void clearLockSuccess() {

    }

    @Override
    public void showMsg(String msg, int color) {
        msgTxt.setText(msg);
        msgTxt.setTextColor(color);
        AnimUtil.setLeftRightVibratorAnim(msgTxt);
        vibrator.vibrate(new long[]{0, 300, 150, 300}, -1);
    }

    @Override
    public void checkSuccess() {
        jumpActivity(MainActivity.class);
        finish();
    }

    @Override
    public boolean isOpenLock() {
        return true;
    }

    /**
     * 正在输入
     *
     * @param str 当前已输入的文本
     */
    @Override
    public void input(String str) {
        msgTxt.setText("输入您的密码");
        msgTxt.setTextColor(R.color.black);
    }

    /**
     * 输入完成
     *
     * @param str 当前已输入的文本
     */
    @Override
    public void end(String str) {
        presenter.checkLockTxt(str, Content.LOCK_STATE_CHECK);
    }
}
