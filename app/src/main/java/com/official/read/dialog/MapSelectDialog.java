package com.official.read.dialog;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;

import com.official.read.R;
import com.official.read.base.BaseDialog;

/**
 * com.official.read.dialog
 * Created by ZP on 2017/9/30.
 * description: 废弃
 * version: 1.0
 */

@Deprecated
public class MapSelectDialog extends BaseDialog implements RadioGroup.OnCheckedChangeListener {

    int checkId;
    RadioGroup group;

    public static MapSelectDialog newInstance(int checkId) {

        Bundle args = new Bundle();
        args.putInt("checkId", checkId);
        MapSelectDialog fragment = new MapSelectDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getDialogType() {
        return DIALOG_CENTER;
    }

    @Override
    protected int getDialogContentView() {
        return R.layout.dialog_map_select;
    }

    @Override
    protected void initDialogView(View view, Bundle bundle) {
        group = $(R.id.dialog_map_group);
        group.setOnCheckedChangeListener(this);
        this.checkId = getArguments().getInt("checkId", -1);
        $(R.id.dialog_map_ok).setOnClickListener(this);
        $(R.id.dialog_map_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_map_ok:
                if(checkId != -1){
                    if (listener != null) {
                        listener.dialogClick(v, checkId);
                    }
                }
                break;
            case R.id.dialog_map_cancel:
                break;
        }
        dismiss();
    }

    @Override
    protected void setDialogProperty() {
        getDialog().setCancelable(false);
        setDialog((int) (getWidth() * 0.85), 0);
    }

    @Override
    protected void destroyAll() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        this.checkId = checkedId;
    }
}
