package com.official.read.base;

import com.official.read.util.Toaster;
import com.official.read.weight.attr.CollapsingToolbarLayoutAttr;
import com.official.read.weight.attr.FloatingActionButtonAttr;
import com.official.read.weight.attr.SuperTextViewAttr;
import com.official.themelibrary.SkinConfig;
import com.official.themelibrary.attr.SkinAttr;
import com.official.themelibrary.loader.SkinManager;
import com.official.themelibrary.utils.SkinFileUtils;

import org.litepal.LitePalApplication;

import java.io.File;
import java.io.IOException;

/**
 * com.official.read.base
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class BaseApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Toaster.init(getApplicationContext());
        initSkinLoader();
        if (!SkinConfig.isInNightMode(this)) {
            SkinManager.getInstance().loadSkin(null);
        } else {
            SkinManager.getInstance().NightMode();
        }
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.addSupportAttr("superTextViewAttr", new SuperTextViewAttr());
        SkinConfig.addSupportAttr("toolbarLayoutAttr", new CollapsingToolbarLayoutAttr());
        SkinConfig.addSupportAttr("actionButtonAttr", new FloatingActionButtonAttr());
        SkinConfig.enableGlobalSkinApply();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        setUpSkinFile();
        SkinManager.getInstance().init(this);
    }

    private void setUpSkinFile() {
        try {
            String[] skinFiles = getAssets().list(SkinConfig.SKIN_DIR_NAME);
            for (String fileName : skinFiles) {
                File file = new File(SkinFileUtils.getSkinDir(this), fileName);
                if (!file.exists())
                    SkinFileUtils.copySkinAssetsToDir(this, fileName, SkinFileUtils.getSkinDir(this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
