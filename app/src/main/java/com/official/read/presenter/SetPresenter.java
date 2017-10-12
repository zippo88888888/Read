package com.official.read.presenter;

import android.content.Intent;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/18.
 * description:
 * version: 1.0
 */

public interface SetPresenter {

    void getAnimState();

    void setAnimState(boolean isOpen);

    void getLockState();

    void setLockState(boolean isFace);

    void checkErrorState(boolean state);

    void getErrorState();

    void setErrorState(boolean isError);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onResume();

}
