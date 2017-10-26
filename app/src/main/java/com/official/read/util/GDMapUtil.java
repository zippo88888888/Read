package com.official.read.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.amap.api.services.core.PoiItem;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.official.read.R;
import com.official.read.content.Content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * com.test.zzs.text.util.baidu_map
 * Created by ZP on 2017/4/10.
 * description:
 * version: 1.0
 */
public class GDMapUtil {

    private final static String ANDROID_MAP = "androidamap://navi?sourceApplication=";
    /** 高德包名 */
    public final static String GAO_DE_PAGE = "com.autonavi.minimap";
    private final static String ACTION_VIEW = "android.intent.action.VIEW";

    /**
     * 启动高德App进行导航
     *
     * @param appName 必填 第三方调用应用名称。如 amap
     * @param poiName 非必填 POI 名称
     * @param lat     必填 纬度
     * @param lon     必填 经度
     * @param dev     必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style   必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；
     *                5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    public static void goToGaoDeMap(Context context, String appName,
                                     String poiName, String lat, String lon,
                                     String dev, String style) {
        StringBuffer sb = new StringBuffer(ANDROID_MAP);
        sb.append(appName);
        if (!TextUtils.isEmpty(poiName)) {
            sb.append("&poiname=").append(poiName);
        }
        sb.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);
        Intent intent = new Intent(ACTION_VIEW, android.net.Uri.parse(sb.toString()));
        intent.setPackage(GAO_DE_PAGE);
        context.startActivity(intent);
    }

    public static class MyPoiOverlay {
        private Context con;
        private AMap map;
        private List<PoiItem> items;
        private ArrayList<Marker> mPoiMarks = new ArrayList<>();

        public MyPoiOverlay(Context context, AMap amap, List<PoiItem> list) {
            con = context;
            map = amap;
            items = list;
        }

        /**
         * 销毁
         */
        public void clear() {
            if (items != null) {
                items.clear();
            }
            if (mPoiMarks != null) {
                mPoiMarks.clear();
            }
        }

        /**
         * 添加Marker到地图中。
         */
        public void addToMap() {
            for (int i = 0; i < items.size(); i++) {
                Marker marker = map.addMarker(getMarkerOptions(i));
                PoiItem item = items.get(i);
                marker.setObject(item);
                mPoiMarks.add(marker);
            }
        }

        /**
         * 去掉PoiOverlay上所有的Marker。
         */
        public void removeFromMap() {
            for (Marker mark : mPoiMarks) {
                mark.remove();
            }
        }

        /**
         * 移动镜头到当前的视角。
         */
        public void zoomToSpan() {
            if (items != null && items.size() > 0) {
                if (map == null)
                    return;
                LatLngBounds bounds = getLatLngBounds();

                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        }

        private LatLngBounds getLatLngBounds() {
            LatLngBounds.Builder b = LatLngBounds.builder();
            for (int i = 0; i < items.size(); i++) {
                b.include(new LatLng(items.get(i).getLatLonPoint().getLatitude(),
                        items.get(i).getLatLonPoint().getLongitude()));
            }
            return b.build();
        }

        private MarkerOptions getMarkerOptions(int index) {
            LatLng latLng = new LatLng(
                    items.get(index).getLatLonPoint().getLatitude(),
                    items.get(index).getLatLonPoint().getLongitude());

            return new MarkerOptions()
                    .position(latLng)
                    .title(getTitle(index)).snippet(getSnippet(index))
                    .icon(getBitmapDescriptor(index));
        }

        public String getTitle(int index) {
            return items.get(index).getTitle();
        }

        public String getSnippet(int index) {
            return items.get(index).getSnippet();
        }

        /**
         * 从marker中得到poi在list的位置。
         *
         * @param marker 一个标记的对象。
         * @return 返回该marker对应的poi在list的位置。
         */
        public int getPoiIndex(Marker marker) {
            for (int i = 0; i < mPoiMarks.size(); i++) {
                if (mPoiMarks.get(i).equals(marker)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 返回第index的poi的信息。
         * @param index 第几个poi。
         * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
         */
        public PoiItem getPoiItem(int index) {
            if (index < 0 || index >= items.size()) {
                return null;
            }
            return items.get(index);
        }

        public BitmapDescriptor getBitmapDescriptor(int index) {
            if (index < 10) {
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(con.getResources(), Content.MARKERS[index]));
                return icon;
            } else {
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(con.getResources(), R.drawable.error));
                return icon;
            }
        }
    }

}
