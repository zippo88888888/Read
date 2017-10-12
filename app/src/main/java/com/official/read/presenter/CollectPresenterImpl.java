package com.official.read.presenter;

import android.view.View;

import com.official.read.base.BasePresenterImpl;
import com.official.read.content.bean.CollectBean;
import com.official.read.dialog.CommonDialog;
import com.official.read.model.CollectModel;
import com.official.read.model.CollectModelImpl;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;
import com.official.read.view.CollectView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public class CollectPresenterImpl extends BasePresenterImpl<CollectView> implements CollectPresenter {

    private CollectModel model;
    private SystemModel themeModel;

    public CollectPresenterImpl() {
        model = new CollectModelImpl();
        themeModel = new SystemModelImpl();
    }

    @Override
    public void getCollectData() {
        List<CollectBean> collectData = model.getCollectData();
        if (collectData != null && collectData.size() > 0) {
            getMvpView().setCollectData(collectData);
        } else {
            getMvpView().noAnywayData();
        }
    }

    @Override
    public void deleteCollect(final CollectBean bean, final int position) {
        boolean collectState = model.getCancelCollectState();
        if (collectState) {
            delete(bean, position);
        } else {
            CommonDialog commonDialog = new CommonDialog(CommonDialog.DIALOG_BUTTON_THREE, getMvpView().getBaseViewContext());
            commonDialog.showDialog3(new CommonDialog.DialogClickListener() {
                @Override
                public void click1() {
                    delete(bean, position);
                }

                @Override
                public void click3() {
                    SPUtil.put(SPUtil.CANCEL_COLLECT, true);
                    delete(bean, position);
                }
            }, "你确定要取消收藏吗？", "取消收藏", "不取消", "不再提醒");
        }
    }

    private void delete(CollectBean bean, int position) {
        int i = DataSupport.deleteAll(CollectBean.class, "fID=?", bean.fID);
        if (i > 0) {
            Toaster.makeText("操作成功");
            getMvpView().deleteSuccess(bean, position);
        } else {
            Toaster.makeText("操作失败");
        }
    }

    @Override
    public void checkSkipAnim(CollectBean bean, View view) {
        boolean animSet = themeModel.getAnimSet();
        if (animSet) {
            getMvpView().skipNotUseAnim(bean);
        } else {
            getMvpView().skipUseAnim(bean, view);
        }
    }
}
