package com.official.read.dialog;

import android.os.Bundle;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.official.read.R;
import com.official.read.adapter.JusticeAdapter;
import com.official.read.base.BaseDialog;
import com.official.read.content.bean.JusticeBean;
import com.official.read.util.RecyclerUtil;

import java.io.Serializable;
import java.util.List;

/**
 * com.official.read.dialog
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public class JusticeDialog extends BaseDialog implements OnItemClickListener {

    LuRecyclerView recyclerView;
    List<JusticeBean> data;
    JusticeAdapter adapter;

    public static JusticeDialog newInstance(List<JusticeBean> data) {
        Bundle args = new Bundle();
        args.putSerializable("data", (Serializable) data);
        JusticeDialog fragment = new JusticeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getDialogType() {
        return DIALOG_BOTTOM;
    }

    @Override
    protected int getDialogView() {
        return R.layout.dialog_justice;
    }

    @Override
    protected void initDialogView(View view, Bundle bundle) {
        data = (List<JusticeBean>) getArguments().getSerializable("data");
        recyclerView = $(R.id.dialog_justice_recyclerView);
        adapter = new JusticeAdapter(getContext(), data);
        LuRecyclerViewAdapter lRecyclerViewAdapter = new LuRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        RecyclerUtil.setLuRecyclerViewDisplay(getContext(), lRecyclerViewAdapter, recyclerView);
        recyclerView.setRefreshing(false);
        recyclerView.setLoadMoreEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void setDialogProperty() {

    }

    @Override
    protected void destroyAll() {
        if (data != null) {
            data.clear();
            data = null;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        JusticeBean item = adapter.getItem(position);
        if (listener != null) {
            listener.dialogClick(view, item);
        }
        dismiss();
    }
}
