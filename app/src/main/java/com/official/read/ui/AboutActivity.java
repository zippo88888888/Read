package com.official.read.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.content.Content;
import com.official.read.content.URL;
import com.official.read.dialog.TelDialog;
import com.official.read.util.AndroidUtil;
import com.official.read.util.StringUtil;
import com.official.read.util.UpdateNewUtil;
import com.official.read.weight.lu_recycler_view.LuActivity;
import com.official.read.util.Toaster;

public class AboutActivity extends BaseActivity {

    boolean isNew = false;
    TextView version;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        setTitle("关于");
        $(R.id.about_tel).setOnClickListener(this);
        $(R.id.about_KY).setOnClickListener(this);
        $(R.id.about_home).setOnClickListener(this);
        $(R.id.about_diy_page).setOnClickListener(this);
        $(R.id.about_check).setOnClickListener(this);
        version = (TextView) $(R.id.about_version);
        version.setText("V " + AndroidUtil.getAppVersionName(this));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.about_KY:
                ArrayMap<String, Object> map = new ArrayMap<>();
                map.put("url", "file:///android_asset/license.html");
                jumpActivity(map, WebViewActivity.class);
                break;
            case R.id.about_tel:
                TelDialog telDialog = new TelDialog();
                telDialog.setDialogItemClickListener(this);
                telDialog.show(getSupportFragmentManager(), Content.DIALOG_TEL_TAG);
                break;
            case R.id.about_home:
                jumpActivity(LuActivity.class);
                break;
            case R.id.about_diy_page:
                jumpActivity(DIYActivity.class);
                break;
            case R.id.about_check:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkNew();
                        Toaster.makeText("已是最新版本");
                    }
                }, 1500);
                break;
        }
    }

    private void checkNew() {
        if (isNew) {
            UpdateNewUtil updateNewUtil = new UpdateNewUtil(this);
            updateNewUtil.showDialog("", "1、签到，抽奖震撼上线了。新品多多，诚意满满" +
                    "\n2、程序员小哥说：我们已经修复了N项问题了，保证流畅运行\n3、改动太多太多，等你来发现");
        }
    }

    @Override
    public void dialogClick(View view, Object obj) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("url", StringUtil.getTelMsg(getResources().getString(R.string.tel_gitHub)));
        jumpActivity(map, WebViewActivity.class);
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
}
