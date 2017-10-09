package com.official.read.ui;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.coorchice.library.SuperTextView;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.mobike.library.MobikeView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.util.AndroidUtil;

public class EasterEggActivity extends BaseActivity implements
        CompoundButton.OnCheckedChangeListener, Runnable {

    private MobikeView mobikeView;
    private SensorManager sensorManager;
    private Sensor defaultSensor;

    int[] colors = {
            R.color.red,
            R.color.pink,
            R.color.violet,
            R.color.blue,
            R.color.green,
            R.color.gray,
            R.color.black
    };

    SwitchCompat bombSwitch;

    @Override
    protected int getContentView() {
        return R.layout.activity_easter_egg;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("Easter Egg");
        mobikeView = (MobikeView) $(R.id.eggMobikeView);
        bombSwitch = (SwitchCompat) $(R.id.eggBomb);

        bombSwitch.setOnCheckedChangeListener(this);

        // 密度
        mobikeView.getmMobike().setDensity(3.0f);
        // 摩擦力  0-1
        mobikeView.getmMobike().setFriction(0.3f);
        // 损失   0-1
        mobikeView.getmMobike().setRestitution(0.3f);
        // 比率
        mobikeView.getmMobike().setRatio(50);
//        mobikeView.getmMobike().update();

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                (120, 120);
        layoutParams.gravity = Gravity.TOP;
        for (int i = 0; i < colors.length; i++) {
            SuperTextView viewS = getViewS();
            viewS.setTag(R.id.mobike_view_circle_tag, true);
            int color = getResources().getColor(colors[i]);
            viewS.setSolid(color);
            viewS.setText((i + 1) + "");
            mobikeView.addView(viewS, layoutParams);
        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private SuperTextView getViewS() {
        SuperTextView textView = new SuperTextView(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(120, 120);
        textView.setLayoutParams(layoutParams);
        textView.setCorner(60);
        textView.setLeftBottomCornerEnable(true);
        textView.setLeftTopCornerEnable(true);
        textView.setRightTopCornerEnable(true);
        textView.setRightBottomCornerEnable(true);
//        textView.setTextStrokeWidth(1f);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected LuRecyclerView getLuRecyclerView() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobikeView.getmMobike().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mobikeView.getmMobike().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listerner);
    }

    private SensorEventListener listerner = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float x =  event.values[0];
                float y =   event.values[1] * 2.0f;
                mobikeView.getmMobike().onSensorChanged(-x,y);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.eggBomb:
                if (isChecked) {
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(500);
                    mobikeView.getmMobike().random();
                    new Handler().postDelayed(this, 500);
                }
                break;
        }
    }

    @Override
    public void run() {
        bombSwitch.setChecked(false);
    }
}
