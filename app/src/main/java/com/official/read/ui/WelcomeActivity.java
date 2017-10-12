package com.official.read.ui;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.content.Content;
import com.official.read.dialog.CommonDialog;
import com.official.read.util.NetworkUtil;
import com.official.read.util.PermissionUtil;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private Timer timer;
    private int index = 0;

    @Override
    protected int getContentView() {
        isSetBar = false;
        return R.layout.activity_welcome;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // 权限判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            startSkip();
        }
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

    private void startSkip() {
        if (NetworkUtil.isNetworkConnected(this)) {
            timer = new Timer();
            timer.schedule(task, 0, 1000);
        } else {
            new CommonDialog(CommonDialog.DIALOG_BUTTON_ONE, this)
                    .showDialog1(new CommonDialog.DialogClickListener(){
                        @Override
                        public void click1() {
                            finish();
                        }
                    }, "当前无网络连接！", "shit");
        }
    }

    private void checkPermission() {
        if (PermissionUtil.hasPermission(WelcomeActivity.this, PermissionUtil.WRITE_EXTERNAL_STORAGE)) {
            PermissionUtil.requestPermission(WelcomeActivity.this, PermissionUtil.STORAGE_CODE, PermissionUtil.WRITE_EXTERNAL_STORAGE);
        } else {
            startSkip();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == PermissionUtil.STORAGE_CODE){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toaster.makeText("您已拒绝程序申请读写权限，部分功能将无法使用！程序已退出，您可到设置中打开文件读写权限！");
                finish();
            } else {
                startSkip();
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (index == 3) {
                if (timer != null) {
                    timer.cancel();
                }
                boolean isOpen = (boolean) SPUtil.get(SPUtil.OPEN_LOCK, false);
                if (isOpen) {
                    jumpActivity(OpenLockActivity.class);
                } else {
                    jumpActivity(MainActivity.class);
                }
                finish();
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            index ++;
            handler.sendEmptyMessage(0);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(task);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
