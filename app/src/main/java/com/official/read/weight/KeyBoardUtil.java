package com.official.read.weight;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v4.util.ArrayMap;

import com.official.read.R;
import com.official.read.util.L;

import java.lang.ref.SoftReference;

/**
 * com.app.myview.util
 * Created by ZP on 2017/9/25.
 * description: 自定义输入法
 * version: 1.0
 */

public class KeyBoardUtil implements KeyboardView.OnKeyboardActionListener {

    public static final String KEY_BOARD_VAL_OK = "ok";
    public static final String KEY_BOARD_VAL_DEL = "del";

    private static final int KEY_BOARD_ONE = 10001;
    private static final int KEY_BOARD_TWO = 10002;
    private static final int KEY_BOARD_THREE = 10003;
    private static final int KEY_BOARD_FOUR = 10004;
    private static final int KEY_BOARD_FIVE = 10005;
    private static final int KEY_BOARD_SIX = 10006;
    private static final int KEY_BOARD_SEVEN = 10007;
    private static final int KEY_BOARD_EIGHT = 10008;
    private static final int KEY_BOARD_NINE = 10009;
    private static final int KEY_BOARD_ZERO = 10000;
    private static final int KEY_BOARD_OK = 11000;
    private static final int KEY_BOARD_DEL = 10010;

    private KeyBoardOutPutListener keyBoardOutPutListener;
    public void setKeyBoardOutPutListener(KeyBoardOutPutListener keyBoardOutPutListener) {
        this.keyBoardOutPutListener = keyBoardOutPutListener;
    }

    /**
     * 防止Context的持续引用从而导致内存溢出
     */
    private ArrayMap<String, SoftReference<Context>> mapContext = new ArrayMap<>();

    public KeyBoardUtil(Context context) {
        SoftReference<Context> softReference = new SoftReference<>(context);
        mapContext.put("context", softReference);
    }

    public void showKeyBoard(KeyboardView keyboardView) {
        if (keyboardView == null) {
            throw new NullPointerException("KeyboardView is not null");
        }
        Context context = getContext();
        if (context == null) {
            L.e("context is null");
            return;
        }
        Keyboard keyboard = new Keyboard(context, R.xml.pay_key_board);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(this);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (keyBoardOutPutListener == null) {
            return;
        }
        switch (primaryCode) {
            case KEY_BOARD_ONE:
                keyBoardOutPutListener.outPut("1");
                break;
            case KEY_BOARD_TWO:
                keyBoardOutPutListener.outPut("2");
                break;
            case KEY_BOARD_THREE:
                keyBoardOutPutListener.outPut("3");
                break;
            case KEY_BOARD_FOUR:
                keyBoardOutPutListener.outPut("4");
                break;
            case KEY_BOARD_FIVE:
                keyBoardOutPutListener.outPut("5");
                break;
            case KEY_BOARD_SIX:
                keyBoardOutPutListener.outPut("6");
                break;
            case KEY_BOARD_SEVEN:
                keyBoardOutPutListener.outPut("7");
                break;
            case KEY_BOARD_EIGHT:
                keyBoardOutPutListener.outPut("8");
                break;
            case KEY_BOARD_NINE:
                keyBoardOutPutListener.outPut("9");
                break;
            case KEY_BOARD_ZERO:
                keyBoardOutPutListener.outPut("0");
                break;
            case KEY_BOARD_OK:
                keyBoardOutPutListener.outPut(KEY_BOARD_VAL_OK);
                break;
            case KEY_BOARD_DEL:
                keyBoardOutPutListener.outPut(KEY_BOARD_VAL_DEL);
                break;
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private Context getContext() {
        SoftReference<Context> softReference = mapContext.get("context");
        if (softReference != null) {
            Context context = softReference.get();
            if (context != null) {
                return context;
            }
        }
        return null;
    }

    public interface KeyBoardOutPutListener {
        void outPut(String value);
    }
}
