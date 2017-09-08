package com.official.read.dialog;

import android.os.Bundle;
import android.view.View;

import com.official.read.R;
import com.official.read.base.BaseDialog;
import com.official.read.util.AndroidUtil;
import com.official.read.util.SPUtil;
import com.official.read.util.StringUtil;
import com.official.read.util.Toaster;

/**
 * com.official.read.dialog
 * Created by ZP on 2017/9/8.
 * description:
 * version: 1.0
 */

public class TelDialog extends BaseDialog {

    @Override
    protected int getDialogType() {
        return DIALOG_CENTER;
    }

    @Override
    protected int getDialogContentView() {
        return R.layout.dialog_tel;
    }

    @Override
    protected void initDialogView(View view, Bundle bundle) {
        $(R.id.dialog_tel_qq).setOnClickListener(this);
        $(R.id.dialog_tel_email).setOnClickListener(this);
        $(R.id.dialog_tel_gitHub).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.dialog_tel_qq:
                AndroidUtil.copy(StringUtil.getTelMsg(getResources().getString(R.string.tel_qq)), getContext());
                Toaster.makeText("已复制到粘贴板");
                break;
            case R.id.dialog_tel_email:
                AndroidUtil.copy(StringUtil.getTelMsg(getResources().getString(R.string.tel_email)), getContext());
                Toaster.makeText("已复制到粘贴板");
                break;
            case R.id.dialog_tel_gitHub:
                listener.dialogClick(v, v.getId());
                break;
        }
        dismiss();
    }

    @Override
    protected void setDialogProperty() {
        setDialogAnimations(android.R.style.Animation_Translucent);
        setDialog((int) (getWidth() * 0.8), 0);
    }

    @Override
    protected void destroyAll() {

    }
}
