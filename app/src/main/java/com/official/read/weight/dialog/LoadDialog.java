package com.official.read.weight.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.util.AndroidUtil;

/**
 * com.app.myview.view.dialog
 * Created by ZP on 2017/10/24.
 * description:
 * version: 1.0
 */

public class LoadDialog extends AlertDialog {

    private TextView titleTxt;
    private String title;
    private LoadingView loadingView;

    public LoadDialog(Context context) {
        super(context);
    }

    public LoadDialog(Context context, String title) {
        this(context);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    private View getContentView() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);

        int padding = AndroidUtil.dip2px(10f);
        titleTxt = new TextView(getContext());
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleTxt.setLayoutParams(titleParams);
        titleTxt.setGravity(Gravity.CENTER);
        titleTxt.setPadding(padding, padding, padding, padding);
        titleTxt.setTextSize(18f);
        if (title != null && title.length() > 0) {
            titleTxt.setText(title);
        } else {
            titleTxt.setText(R.string.loading);
        }

        loadingView = new LoadingView(getContext());
        LinearLayout.LayoutParams loadLayout = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadLayout.bottomMargin = padding;
        loadingView.setLayoutParams(loadLayout);
        loadingView.setLoadCount(7)
                .setAnimTime(700)
                .setLoadSwingL(AndroidUtil.dip2px(40f))
                .complete();

        layout.addView(titleTxt);
        layout.addView(loadingView);
        return layout;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.startAnim();
            }
        }, 10);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        loadingView.endAnim();
    }

}
