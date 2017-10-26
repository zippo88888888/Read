package com.official.read.presenter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.util.AndroidUtil;
import com.official.read.util.GDMapUtil;
import com.official.read.util.Toaster;
import com.official.read.view.GDMapView;

import java.util.ArrayList;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/9/28.
 * description:
 * version: 1.0
 */

public class GDMapPresenterImpl extends BasePresenterImpl<GDMapView> implements GDMapPresenter {

    // 导航
    private int navigateItem = 0;
    // 筛选内容
    private int selectItem = -1;

    private SystemModel themeModel;

    public GDMapPresenterImpl() {
        this.themeModel = new SystemModelImpl();
    }

    @Override
    public String getTheme() {
        return themeModel.getTheme();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation, boolean isAutoLocation) {
        getMvpView().dismissDialog();
        if (aMapLocation != null) {
            if (isAutoLocation) {
                getMvpView().change(aMapLocation);
            } else {
                getMvpView().moveToChange(aMapLocation);
            }
        } else {
            Toaster.makeText("定位失败！");
        }
    }

    @Override
    public void checkNavigateData(LatLonPoint nowPoint, final LatLonPoint point) {
        if (nowPoint != null && nowPoint.getLatitude() != Content.NO_LAT_LON_POINT_DATA) {
            String[] type;
            if (AndroidUtil.isInstallByRead(GDMapUtil.GAO_DE_PAGE)) {
                type = new String[]{"使用高德导航(推荐)", "驾车", "步行", "骑行"};
            } else {
                type = new String[]{"驾车", "步行", "骑行"};
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getMvpView().getBaseViewContext());
            builder.setTitle("请选择导航方式");
            builder.setCancelable(false);
            builder.setSingleChoiceItems(type, navigateItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    navigateItem = which;
                }
            });
            builder.setPositiveButton("开始导航", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (AndroidUtil.isInstallByRead(GDMapUtil.GAO_DE_PAGE)) {
                        if (navigateItem == 0) {
                            GDMapUtil.goToGaoDeMap(getMvpView().getBaseViewContext(), "", "Price", point.getLatitude() + "",
                                    point.getLongitude() + "", "0", "2");
                        } else if (navigateItem == 1) {
                            getMvpView().startNavigate(Content.NAVIGATE_CAR);
                        } else if (navigateItem == 2) {
                            getMvpView().startNavigate(Content.NAVIGATE_WALK);
                        } else if (navigateItem == 3) {
                            getMvpView().startNavigate(Content.NAVIGATE_CYCLING);
                        }
                    } else {
                        if (navigateItem == 0) {
                            getMvpView().startNavigate(Content.NAVIGATE_CAR);
                        } else if (navigateItem == 1) {
                            getMvpView().startNavigate(Content.NAVIGATE_WALK);
                        } else if (navigateItem == 2) {
                            getMvpView().startNavigate(Content.NAVIGATE_CYCLING);
                        }
                    }
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } else {
            Toaster.makeText("定位中，请稍后...");
        }
    }

    @Override
    public void createSelectDialog() {
        String[] type = {"地铁","公交","银行","医疗","教育","餐饮","购物"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getMvpView().getBaseViewContext());
        builder.setTitle("请选择筛选内容");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(type, selectItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectItem = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (selectItem) {
                    case 0:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_METRO);
                        break;
                    case 1:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_BUS);
                        break;
                    case 2:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_BANK);
                        break;
                    case 3:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_MEDICAL);
                        break;
                    case 4:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_EDUCATION);
                        break;
                    case 5:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_EAT);
                        break;
                    case 6:
                        getMvpView().showMarkerByKey(Content.MARKER_KEY_SHOPPING);
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onPoiSearched(PoiResult result, int code) {
        getMvpView().dismissDialog();
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                ArrayList<PoiItem> items = result.getPois();
                if (items != null && items.size() > 0) {
                    getMvpView().showMarker(items, result);
                } else {
                    // 当搜索不到poi item数据时，会返回含有搜索关键字的城市信息
//                    List<SuggestionCity> cityList = result.getSearchSuggestionCitys();
                    Toaster.makeText("对不起，没有搜索到相关数据");
                }

            } else {
                Toaster.makeText("对不起，没有搜索到相关数据");
            }
        } else {
            Toaster.makeText("对不起，没有搜索到相关数据");
        }
    }

    @Override
    public void checkPoiOverlay(GDMapUtil.MyPoiOverlay poiOverlay) {
        if (poiOverlay != null) {
            getMvpView().removeFromMap();
        }
    }

    @Override
    public void checkAppBarState(boolean isFull) {
        if (isFull) { // 退出全屏
            getMvpView().normalShow();
        } else {
            getMvpView().fullShow();
        }
    }
}
