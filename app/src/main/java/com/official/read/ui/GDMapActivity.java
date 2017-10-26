package com.official.read.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.presenter.GDMapPresenterImpl;
import com.official.read.util.AndroidUtil;
import com.official.read.util.GDMapUtil;
import com.official.read.util.L;
import com.official.read.util.Toaster;
import com.official.read.util.anim.AnimUtil;
import com.official.read.view.GDMapView;

import java.util.ArrayList;

public class GDMapActivity extends BaseActivity<GDMapPresenterImpl, GDMapView> implements GDMapView,
        GeocodeSearch.OnGeocodeSearchListener, AMapLocationListener, AMap.OnMapClickListener,
        PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener {

    AppBarLayout barLayout;
    boolean isFull = false;

    private MapView mapView;
    private AMap map;
    // 目标位置
    private LatLonPoint point;
    // 当前定位位置
    private LatLonPoint nowPoint;

    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    // 地理编码类
    private GeocodeSearch search;

    // Poi查询条件类
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private GDMapUtil.MyPoiOverlay poiOverlay;// poi图层

    // 是否为自动定位
    private boolean isAutoLocation = true;

    private String title;

    @Override
    protected int getContentView() {
        return R.layout.activity_gd_map;
    }

    @Override
    protected GDMapPresenterImpl initPresenter() {
        return new GDMapPresenterImpl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("位置与周边");
        setToolbarInMenu();
        setOnMenuItemClickListener(this);
        setNavigationIconClickListener(this);

        barLayout = $(R.id.tool_app_barLayout);
        mapView = $(R.id.gd_map_mapView);

        $(R.id.map_select).setOnClickListener(this);
        $(R.id.map_location).setOnClickListener(this);
        $(R.id.map_full_screen).setOnClickListener(this);

        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);

        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(false);

        search = new GeocodeSearch(this);
        search.setOnGeocodeSearchListener(this);
        $(R.id.map_rightLayout).setAlpha(0.8f);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        showDialog(null);
        point = getIntent().getParcelableExtra("point");
        title = getIntent().getStringExtra("title");
        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
        L.e("目标位置--->>>" + latLng.toString());
        animateCamera(latLng);
        targetIconToMap(latLng, title);

        nowPoint = new LatLonPoint(Content.NO_LAT_LON_POINT_DATA, Content.NO_LAT_LON_POINT_DATA);

        // 初始化定位
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        setOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(this);
        // 启动定位
        locationClient.startLocation();
        isAutoLocation = true;
    }

    private void setOption() {
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        locationOption.setHttpTimeOut(10000);
        //可选，设置定位间隔。默认为2秒
        locationOption.setInterval(3000);
        //可选，设置是否单次定位。默认是false
        locationOption.setOnceLocation(true);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        // 定位完成后处理数据
        presenter.onLocationChanged(aMapLocation, isAutoLocation);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_select:
                presenter.createSelectDialog();
                break;
            case R.id.map_location:
                showDialog(null);
                setOption();
                locationClient.setLocationOption(locationOption);
                isAutoLocation = false;
                // 启动定位
                locationClient.startLocation();
                break;
            case R.id.map_full_screen:
                presenter.checkAppBarState(isFull);
                break;
            default:
                back();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_map_line: // 路线
                Toaster.makeText("路线");
                break;
            case R.id.menu_map_dh: // 导航
                presenter.checkNavigateData(nowPoint, point);
                break;
        }
        return true;
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
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // 销毁定位
        locationClient.onDestroy();
        locationClient = null;
        locationOption = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    /**
     * 目标显示在地图上
     */
    private void targetIconToMap(LatLng latLng, String msg) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("位置信息").snippet(msg);
        //设置Marker可拖动
        markerOption.draggable(false);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.target_map_icon)));
        //设置marker平贴地图效果
        markerOption.setFlat(false);
        map.addMarker(markerOption);
    }

    /**
     * 地图移动至目标位置
     * @param latLng 目标经纬度
     */
    private void animateCamera(LatLng latLng) {
        //参数是：中心点坐标、缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        CameraPosition position = new CameraPosition(latLng, 15, 0, 0);
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(update, 1000, null);
    }


    /**
     * 自动定位(进入就定位)
     * @param location 定位结果
     */
    @Override
    public void change(AMapLocation location) {
        // 经度
        nowPoint.setLongitude(location.getLongitude());
        // 纬度
        nowPoint.setLatitude(location.getLatitude());

        L.e("起点位置--->>>" + "lat/lng: (" + location.getLatitude() + "," + location.getLongitude() + ")");

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(location.getLatitude(), location.getLongitude()));
//        markerOption.title("当前您的位置").snippet(location.getDistrict() + location.getStreet() + location.getStreetNum());
        markerOption.draggable(false);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.location_marker)));
        //设置marker平贴地图效果
        markerOption.setFlat(false);
        map.addMarker(markerOption);
    }

    /**
     * 手动定位
     * @param location 定位结果
     */
    @Override
    public void moveToChange(AMapLocation location) {
        // 停止定位
        locationClient.stopLocation();
        // 经度
        nowPoint.setLongitude(location.getLongitude());
        // 纬度
        nowPoint.setLatitude(location.getLatitude());
        // 手动定位完成后移动至定位中心位置
        animateCamera(new LatLng(location.getLatitude(), location.getLongitude()));
        L.e("手动定位：起点位置--->>>" + "lat/lng: (" + location.getLatitude() + "," + location.getLongitude() + ")");
    }

    /**
     * 导航
     * @param navigateType  导航方式
     */
    @Override
    public void startNavigate(int navigateType) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("point", point);
        map.put("nowPoint", nowPoint);
        map.put("navigateType", navigateType);
        // 将当前的主题色传给导航页面
        map.put("theme", presenter.getTheme());
        jumpActivity(map, NavigateActivity.class);
    }

    /**
     * 根据key Poi搜索数据
     */
    @Override
    public void showMarkerByKey(String key) {
        showDialog(null);
        query = new PoiSearch.Query(key, "", "上海市");
        query.setPageSize(10);
        query.setPageNum(0);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        // 设置搜索区域为以lp点为圆心，其周围2000米范围
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(point.getLatitude(), point.getLongitude()), 2000, true));
        poiSearch.searchPOIAsyn();// 异步搜索
    }


    /**
     * 检查完poi搜索结果后显示Marker
     * @param items     Marker集合
     * @param result    PoiResult，补充
     */
    @Override
    public void showMarker(ArrayList<PoiItem> items, PoiResult result) {
        // 清理之前搜索结果的marker
        presenter.checkPoiOverlay(poiOverlay);
        map.clear();
        // 添加目标Marker至地图
        targetIconToMap(new LatLng(point.getLatitude(), point.getLongitude()), title);
        // 添加当前位置至地图
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(nowPoint.getLatitude(), nowPoint.getLongitude()));
//        markerOption.title("当前您的位置").snippet(location.getDistrict() + location.getStreet() + location.getStreetNum());
        markerOption.draggable(false);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.location_marker)));
        //设置marker平贴地图效果
        markerOption.setFlat(false);

        map.addMarker(markerOption);
        poiOverlay = new GDMapUtil.MyPoiOverlay(this, map, items);
        poiOverlay.addToMap();
        animateCamera(new LatLng(point.getLatitude(), point.getLongitude()));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public void removeFromMap() {
        // 清理之前搜索结果的marker
        poiOverlay.removeFromMap();
    }

    @Override
    public void fullShow() {
        AnimUtil.startAnimForMap(barLayout, false);
        isFull = true;
        setFullScreen(true);
    }

    @Override
    public void normalShow() {
        AnimUtil.startAnimForMap(barLayout, true);
        barLayout.setVisibility(View.VISIBLE);
        isFull = false;
        setFullScreen(false);
    }

    private void setFullScreen(boolean enable) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (enable) {
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        getWindow().setAttributes(lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    /**
     * poi搜索结果回调
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        // 检查poi搜索结果
        presenter.onPoiSearched(poiResult, i);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void dialogClick(View view, Object obj) {
        int checkID = (int) obj;
        switch (checkID) {
            case R.id.dialog_map_metro:
                L.e("地铁");
                break;
            case R.id.dialog_map_bus:
                L.e("公交");
                break;
            case R.id.dialog_map_bank:
                L.e("银行");
                break;
            case R.id.dialog_map_medical:
                L.e("医疗");
                break;
            case R.id.dialog_map_education:
                L.e("教育");
                break;
            case R.id.dialog_map_eat:
                L.e("餐饮");
                break;
            case R.id.dialog_map_shopping:
                L.e("购物");
                break;
        }
    }

}
