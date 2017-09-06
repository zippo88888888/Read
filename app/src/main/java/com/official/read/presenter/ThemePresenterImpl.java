package com.official.read.presenter;

import com.official.read.base.BasePresenterImpl;
import com.official.read.model.ThemeModel;
import com.official.read.model.ThemeModelImpl;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;
import com.official.read.view.ThemeView;
import com.official.themelibrary.SkinLoaderListener;
import com.official.themelibrary.loader.SkinManager;


/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/9.
 * description:
 * version: 1.0
 */

public class ThemePresenterImpl extends BasePresenterImpl<ThemeView> implements ThemePresenter {

    ThemeModel model;

    public ThemePresenterImpl() {
        model = new ThemeModelImpl();
    }

    @Override
    public void changeTheme(final String theme) {
        SkinManager.getInstance().loadSkin(theme, new SkinLoaderListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                // 得到上次主题
                String lastTheme = model.getTheme();
                Toaster.makeText("切换主题成功");
                SPUtil.put(SPUtil.BASE_COLOR, theme);
                SPUtil.put(SPUtil.LAST_COLOR, lastTheme);
            }

            @Override
            public void onFailed(String errMsg) {
                Toaster.makeText("切换失败");
            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

}
