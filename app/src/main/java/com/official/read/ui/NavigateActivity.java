package com.official.read.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.jaeger.library.StatusBarUtil;
import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.L;
import com.official.read.util.TTSController;

import java.util.ArrayList;
import java.util.List;

// 继承自BaseActivity导航页面不显示出来
public class NavigateActivity extends Activity implements AMapNaviListener, AMapNaviViewListener {

    private AMapNaviView mapNaviView;
    private AMapNavi mapNavi;
    private TTSController controller;

    // 目标位置
    private NaviLatLng latLng;
    // 当前位置
    private NaviLatLng nowLatLng;

    private int navigateType;

    // 将当前的主题色传给导航页面
    private String theme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = getIntent();

        theme = intent.getStringExtra("theme");

        LatLonPoint point = intent.getParcelableExtra("point");
        latLng = new NaviLatLng(point.getLatitude(), point.getLongitude());
        L.e("目标位置--->>>" + latLng.toString());

        LatLonPoint nowPoint = intent.getParcelableExtra("nowPoint");
        nowLatLng = new NaviLatLng(nowPoint.getLatitude(), nowPoint.getLongitude());
        L.e("起点位置--->>>" + nowLatLng.toString());

        navigateType = intent.getIntExtra("navigateType", Content.NAVIGATE_CAR);

        //实例化语音引擎
        controller = TTSController.getInstance(getApplicationContext());
        controller.init();

        mapNavi = AMapNavi.getInstance(getApplicationContext());
        mapNavi.addAMapNaviListener(this);
        mapNavi.addAMapNaviListener(controller);


        setContentView(R.layout.activity_navigate);

        mapNaviView = (AMapNaviView) findViewById(R.id.navigateMapView);
        mapNaviView.onCreate(savedInstanceState);

        mapNaviView.setAMapNaviViewListener(this);

        // 设置状态栏颜色
        int color = Content.getBaseColorByTheme(theme);
        int statusColor = getResources().getColor(color);
        StatusBarUtil.setColor(this, statusColor, 225);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapNaviView.onPause();
        //        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        controller.stopSpeaking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mapNavi.stopNavi();
        mapNavi.destroy();
        controller.destroy();

        int color = Content.getBaseColorByTheme(theme);
        int statusColor = getResources().getColor(color);
        StatusBarUtil.setColor(this, statusColor, 0);
    }

    @Override
    public Resources getResources() {
        return getBaseContext().getResources();
    }

    // 以下为导航回调接口

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "导航初始化失败", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功
        L.e("初始化成功");
        switch (navigateType) {
            case Content.NAVIGATE_CAR: //驾车
                int strategy = mapNavi.strategyConvert(true, false, false, false, false);
                L.e("驾车路径初始化中的 strategy的值为：--->>>" + strategy);
                List<NaviLatLng> start = new ArrayList<>();
                start.add(nowLatLng);
                List<NaviLatLng> end = new ArrayList<>();
                end.add(latLng);
                if (mapNavi.calculateDriveRoute(start, end, null, strategy)) {
                    L.e("驾车路径初始化--->>>成功");
                } else {
                    L.e("驾车路径初始化--->>>失败");
                }
                break;
            case Content.NAVIGATE_WALK: // 步行

                if (mapNavi.calculateWalkRoute(nowLatLng, latLng)) {
                    L.e("步行路径初始化--->>>成功");
                } else {
                    L.e("步行路径初始化--->>>失败");
                }
                break;
            case Content.NAVIGATE_CYCLING: // 骑行
                if (mapNavi.calculateRideRoute(nowLatLng, latLng)) {
                    L.e("骑行路径初始化--->>>成功");
                } else {
                    L.e("骑行路径初始化--->>>失败");
                }
                break;

        }
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
//        dismissDialog();
        if (errorInfo == 2) {
            Toast.makeText(this, "网络连接超时", Toast.LENGTH_SHORT).show();
        }
        L.e("路线计算失败：错误码=" + errorInfo);
        L.e("错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        L.e("--------------------------------------------");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //多路径算路成功回调
        L.e("onCalculateRouteSuccess---路径算路成功回调");
        // 开始导航
        mapNavi.startNavi(NaviType.GPS);
//        dismissDialog();

    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调
        L.e("onStartNavi---开始导航回调--type：" + type);
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //地图的模式，锁屏或锁车
    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }


    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //显示车道信息

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(this, "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            L.d("当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(this, "当前在主路", Toast.LENGTH_SHORT).show();

            L.d("当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(this, "当前在辅路", Toast.LENGTH_SHORT).show();

            L.d("当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        L.e("导航页面加载成功");
        L.e("请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }
}
