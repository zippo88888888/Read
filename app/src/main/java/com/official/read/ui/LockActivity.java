package com.official.read.ui;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.presenter.LockPresenterImpl;
import com.official.read.util.anim.AnimUtil;
import com.official.read.view.LockView;
import com.official.read.weight.MyKeyBoardView;

public class LockActivity extends BaseActivity<LockPresenterImpl,LockView> implements LockView,
        MyKeyBoardView.InputTxtListener {

    private KeyboardView keyboardView;
    private MyKeyBoardView myKeyBoardView;

    private TextView msgTxt;

    private int lockState;

    private Vibrator vibrator;

    @Override
    protected int getContentView() {
        return R.layout.activity_lock;
    }

    @Override
    protected LockPresenterImpl initPresenter() {
        return new LockPresenterImpl();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("密码锁");
        keyboardView = $(R.id.lock_keyBoard);
        myKeyBoardView = $(R.id.lock_myKeyBoardView);
        msgTxt = $(R.id.lock_showMsg);
        myKeyBoardView.setInputTxtListener(this);
        dynamicAddView(myKeyBoardView, "myKeyBoardViewAttr", R.color.baseColor);
        vibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myKeyBoardView.setKeyBoardView(keyboardView);
        myKeyBoardView.showKeyBoard(false);
        lockState = getIntent().getIntExtra("lockState", Content.LOCK_STATE_FIRST);
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
        presenter.checkLockTxt(str, lockState);
    }

    @Override
    public int getLockLength() {
        return myKeyBoardView.getLength();
    }

    @Override
    public void setLockSuccess() {
        Intent intent = new Intent(this, SetActivity.class);
        setResult(Content.SET_LOCK_PWD_RESULT_CODE, intent);
        finish();
    }

    @Override
    public void clearLockSuccess() {
        Intent intent = new Intent(this, SetActivity.class);
        setResult(Content.CLEAR_LOCK_PWD_RESULT_CODE, intent);
        finish();
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

    }

    @Override
    public boolean isOpenLock() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
    }
}
