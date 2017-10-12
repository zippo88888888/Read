package com.official.read.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.dialog.CommonDialog;
import com.official.read.presenter.SetPresenterImpl;
import com.official.read.util.ClearCacheUtil;
import com.official.read.util.Toaster;
import com.official.read.view.SetView;

public class SetActivity extends BaseActivity<SetPresenterImpl, SetView> implements
        SetView, CompoundButton.OnCheckedChangeListener {

    SwitchCompat switchCompat;
    SwitchCompat faceCompat;
    TextView cacheSize;

    LinearLayout errorLayout;
    SwitchCompat errorSwitch;

    @Override
    protected int getContentView() {
        return R.layout.activity_set;
    }

    @Override
    protected SetPresenterImpl initPresenter() {
        return new SetPresenterImpl();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("设置");
        errorLayout = $(R.id.set_errorLayout);
        errorSwitch = $(R.id.set_error);
        errorSwitch.setOnCheckedChangeListener(this);
        switchCompat = $(R.id.set_switch);
        switchCompat.setOnCheckedChangeListener(this);
        faceCompat = $(R.id.set_lock);
        faceCompat.setOnCheckedChangeListener(this);
        cacheSize = $(R.id.set_cacheSize);
        $(R.id.set_clear).setOnClickListener(this);
        $(R.id.set_default).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter.checkErrorState(isTabletDevice());
        getSize();
        presenter.getAnimState();
        presenter.getLockState();
    }

    private void getSize() {
        try {
            String size = ClearCacheUtil.getCacheSize(getCacheDir());
            if (!"Byte".equals(size)) {
                cacheSize.setText("（" + size + "）");
            } else {
                cacheSize.setText("（0KB）");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cacheSize.setText("（0KB）");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_clear:
                ClearCacheUtil.cleanInternalCache(this);
                ClearCacheUtil.cleanFiles(this);
                Toaster.makeText("操作成功！如若不成功，可到设置中再次删除哦！");
                cacheSize.setText("（0KB）");
                break;
            case R.id.set_default:
                showToast();
                break;
        }
    }

    private void showToast() {
        new CommonDialog(CommonDialog.DIALOG_BUTTON_TWO, this).showDialog2(
                new CommonDialog.DialogClickListener() {
                    @Override
                    public void click1() {
                        ClearCacheUtil.cleanApplicationData(SetActivity.this);
                        Toaster.makeText("清除成功");
                        onBackPressed();
                    }
                }, "清除后可能会造成程序不稳定，甚至崩溃！确定继续？", "不管，继续清除", "算了，不清了");
    }

    @Override
    public void setAnimState(boolean state) {
        switchCompat.setChecked(state);
    }

    @Override
    public void setLockState(boolean state) {
        faceCompat.setChecked(state);
    }

    @Override
    public void jumpToSetLockActivity() {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra("lockState", Content.LOCK_STATE_FIRST);
        startActivityForResult(intent, Content.SET_LOCK_PWD_REQUEST_CODE);
    }

    @Override
    public void jumpToClearLockActivity() {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra("lockState", Content.LOCK_STATE_CLEAR);
        startActivityForResult(intent, Content.CLEAR_LOCK_PWD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showErrorLayout() {
        errorLayout.setVisibility(View.VISIBLE);
        presenter.getErrorState();
    }

    @Override
    public void setErrorState(boolean state) {
        errorSwitch.setChecked(state);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.set_switch:
                presenter.setAnimState(isChecked);
                break;
            case R.id.set_lock:
                presenter.setLockState(isChecked);
                break;
            case R.id.set_error:
                presenter.setErrorState(isChecked);
                break;
        }
    }
}
