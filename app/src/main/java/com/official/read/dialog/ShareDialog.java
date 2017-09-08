package com.official.read.dialog;


import android.os.Bundle;
import android.view.View;

import com.official.read.R;
import com.official.read.base.BaseDialog;


public class ShareDialog extends BaseDialog {

    @Override
    protected int getDialogType() {
        return DIALOG_BOTTOM;
    }

    @Override
    protected int getDialogContentView() {
        return R.layout.dialog_share;
    }

    @Override
    protected void initDialogView(View view, Bundle bundle) {
        $(R.id.share_qq).setOnClickListener(this);
        $(R.id.share_weibo).setOnClickListener(this);
        $(R.id.share_webChat).setOnClickListener(this);
        $(R.id.share_more).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (listener != null) {
            listener.dialogClick(v, id);
            dismiss();
        }
    }

    @Override
    protected void setDialogProperty() {

    }

    @Override
    protected void destroyAll() {

    }

}
