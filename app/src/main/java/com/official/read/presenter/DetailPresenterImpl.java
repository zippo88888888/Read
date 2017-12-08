package com.official.read.presenter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeResult;
import com.official.read.R;
import com.official.read.base.BaseModel;
import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.content.listener.MyObserver;
import com.official.read.content.bean.CommonBean;
import com.official.read.content.bean.CollectBean;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.bean.RecommendBean;
import com.official.read.content.http.AES;
import com.official.read.model.DetailModel;
import com.official.read.model.DetailModelImpl;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.util.PermissionUtil;
import com.official.read.util.StringUtil;
import com.official.read.util.Toaster;
import com.official.read.view.DetailView;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class DetailPresenterImpl extends BasePresenterImpl<DetailView> implements DetailPresenter {

    // 地理编码查询失败的次数
    private int errorLatLonPoint = 0;

    private BaseModel model;
    private SystemModel themeModel;

    public DetailPresenterImpl() {

        model = BaseModel.METHOD.method(BaseModel.METHOD.DETAIL_MODEL);

        this.themeModel = new SystemModelImpl();
    }

    @Override
    public void getDetailData(String fID) {
        checkNetworkState();
        LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
        mMap.put("hid", fID);
        mMap.put("uid", "");
        if (!mMap.isEmpty()) {
            mMap = AES.getSign(mMap);
        } else {
            mMap.put("sign", AES.getSign(""));
        }
        // 由于使用了反射，所以不能混淆
        model.execute(mMap).subscribe(new MyObserver<CommonBean<DetailBean>>(Content.DISPOSABLE_DETAIL_DATA) {
            @Override
            protected void next(CommonBean<DetailBean> value) {
                DetailBean data = value.data;
                if (isAttachView()) getMvpView().initDetailData(data);
            }

        });
    }

    @Override
    public void checkTelPermission() {
        // 权限判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity context = (Activity) getMvpView().getBaseViewContext();
            if (PermissionUtil.hasPermission(getMvpView().getBaseViewContext(), PermissionUtil.CALL_PHONE)) {
                PermissionUtil.requestPermission(context, PermissionUtil.PHONE_CODE, PermissionUtil.CALL_PHONE);
            } else {
                if (isAttachView()) getMvpView().tel();
            }
        } else {
            if (isAttachView()) getMvpView().tel();
        }
    }

    @Override
    public void checkLocationPermission() {
        // 权限判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity context = (Activity) getMvpView().getBaseViewContext();
            if (PermissionUtil.hasPermission(getMvpView().getBaseViewContext(), PermissionUtil.ACCESS_COARSE_LOCATION)) {
                PermissionUtil.requestPermission(context, PermissionUtil.LOCATION_CODE, PermissionUtil.ACCESS_COARSE_LOCATION);
            } else {
                if (isAttachView()) getMvpView().skipMapView();
            }
        } else {
            if (isAttachView()) getMvpView().skipMapView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == PermissionUtil.PHONE_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toaster.makeText("您已拒绝程序申请拨打电话权限，该功能将暂时无法使用！");
            } else {
                if (isAttachView()) getMvpView().tel();
            }
        } else if (requestCode == PermissionUtil.LOCATION_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toaster.makeText("您已拒绝程序申请定位权限，暂时无法查看相关地图信息！");
            } else {
                if (isAttachView()) getMvpView().skipMapView();
            }
        }
    }

    @Override
    public void isShowOrHidden(boolean isShow) {
        if (isShow) { // 变为显示
            if (isAttachView()) getMvpView().show();
        } else {
            if (isAttachView()) getMvpView().hidden();
        }
    }

    @Override
    public void setLightGridViewData(String data) {
        if(data != null && data.length() > 0){
            String[] tags = data.split("#");
            List<String> list = Arrays.asList(tags);
            if (isAttachView()) getMvpView().setLightGridViewData(list);
        } else {
            if (isAttachView()) getMvpView().hiddenLightGridView();
        }
    }

    @Override
    public void checkDetailBean(DetailBean bean, int state) {
        if (bean != null) {
            if (state == 1) {
                CollectBean connectionBean = new CollectBean();
                connectionBean.date = new Date(System.currentTimeMillis());
                connectionBean.fID = bean.house_id;
                connectionBean.imgURL = bean.imgs.get(0).imgs;
                connectionBean.title = bean.house_name;
                connection(connectionBean);
            } else if (state == 2) {
                checkTelPermission();
            } else {
                checkLocationPermission();
            }
        } else {
            Toaster.makeText("正在请求数据中");
        }
    }

    @Override
    public void checkLatLonPoint(DetailBean bean, LatLonPoint point) {
        if (bean == null) {
            Toaster.makeText("正在请求数据中");
            return;
        }
        if (point == null) {
            if (errorLatLonPoint > 3) {
                Toaster.makeText("当前网络不稳定，请稍后再试！");
            } else {
                if (isAttachView()) getMvpView().showDialog(null);
                errorLatLonPoint ++;
                if (isAttachView()) getMvpView().getLatLonPoint();
            }
        } else {
            checkLocationPermission();
        }
    }

    @Override
    public void checkTop3Value(String deposit, String source, String priority) {
        String d = "--";
        String s = "拍卖房";
        String p = "--";
        if (deposit == null || "".equals(deposit)) {
            d = "--";
        } else {
            double number = Double.parseDouble(deposit);
            String decimal = StringUtil.decimal(number / 10000);
            d = "￥" + decimal + "万";
        }

        if ("1".equals(source)) {
            s = "拍卖房";
        } else {
            s = "物权房";
        }
        if (priority == null || "".equals(priority)) {
            p = "--";
        } else {
            p = priority;
        }
        if (isAttachView()) getMvpView().setTop3Value(d, s, p);
    }

    @Override
    public void checkState(DetailBean bean) {
        // 如果评估价为空就为评估价     否则就为市场参考价（针对所有状态）
        String bidPrice;
        String bidPriceT;
        String qpPrice = "--";
        String qpPriceT = "--";
        String houseTime = "--";

        if (bean.price != null && !"".equals(bean.price)) {
            bidPriceT = "评估价：";
            double number = Double.parseDouble(bean.price);
            String decimal = StringUtil.decimal(number / 10000);
            bidPrice = "￥" + decimal + "万";
        } else {
            bidPriceT = "参考价：";
            if (bean.reserve_price != null && !"".equals(bean.reserve_price)) {
                double number = Double.parseDouble(bean.reserve_price);
                String decimal = StringUtil.decimal(number / 10000);
                bidPrice = "￥" + decimal + "万";
            } else {
                bidPrice = "--";
            }
        }
        if (bean.auction_status == null || "".equals(bean.auction_status)) { // 状态为空
            qpPriceT = "起拍价：";
            try {
                double number = Double.parseDouble(bean.down_payment);
                String decimal = StringUtil.decimal(number / 10000);
                qpPrice = "￥" + decimal + "万";
            } catch (Exception e) {
                qpPrice = "--";
            }
            return;
        }
        int status = Integer.parseInt(bean.auction_status);
        switch (status) {
            case RecommendBean.HOUSE_STATUS_STARTING:  // 正在拍卖
                qpPriceT = "起拍价：";
                try {
                    double number = Double.parseDouble(bean.down_payment);
                    String decimal = StringUtil.decimal(number / 10000);
                    qpPrice = "￥" + decimal + "万";
                } catch (Exception e) {
                    qpPrice = "--";
                }
                houseTime = "正在拍卖";
                break;
            case RecommendBean.HOUSE_STATUS_ABOUNT_TO_BEGIN:  // 即将开始
            case RecommendBean.HOUSE_STATUS_DOWNTEMPO:  // 缓拍
                qpPriceT = "起拍价：";
                try {
                    double number = Double.parseDouble(bean.down_payment);
                    String decimal = StringUtil.decimal(number / 10000);
                    qpPrice = "￥" + decimal + "万";
                } catch (Exception e) {
                    qpPrice = "--";
                }
                // 倒计时
                houseTime = "即将开始 "/* + bean.end_time*/;
                break;
            case RecommendBean.HOUSE_STATUS_ABORTIVE_AUCTION: // 流标
            case RecommendBean.HOUSE_STATUS_BACKOUT:  // 撤拍
                qpPriceT = "起拍价：";
                try {
                    double number = Double.parseDouble(bean.down_payment);
                    String decimal = StringUtil.decimal(number / 10000);
                    qpPrice = "￥" + decimal + "万";
                } catch (Exception e) {
                    qpPrice = "--";
                }
                houseTime = "已结束";
                break;
            case RecommendBean.HOUSE_STATUS_KNOCKDOWN:  // 已成交
                String price = bean.transaction_price;
                if (price == null || "".equals(price) || "0".equals(price)) {
                    qpPriceT = "成交价：";
                    if (bean.down_payment == null || "".equals(bean.down_payment)) {
                        qpPrice = "--";
                    } else {
                        double number = Double.parseDouble(bean.down_payment);
                        String decimal = StringUtil.decimal(number / 10000);
                        qpPrice = "￥" + decimal + "万";
                    }
                } else {
                    qpPriceT = "成交价：";
                    double number = Double.parseDouble(bean.transaction_price);
                    String decimal = StringUtil.decimal(number / 10000);
                    qpPrice = "￥" + decimal + "万";
                }
                houseTime = "已结束";
                break;
        }
        if (isAttachView()) getMvpView().setValue(bidPrice, bidPriceT, qpPrice, qpPriceT, houseTime);
    }

    @Override
    public void checkConnectionByFID(String fID) {
        List<CollectBean> list = model.get(fID);
        if (list != null && list.size() > 0) {
            if (isAttachView()) getMvpView().setConnectionState("已收藏");
        } else {
            if (isAttachView()) getMvpView().setConnectionState("收藏");
        }
    }

    @Override
    public void connection(CollectBean bean) {
        List<CollectBean> list = model.get(bean.fID);
        if(list == null || list.size() <= 0) {
            boolean save = bean.save();
            if (save) {
                Toaster.makeText("收藏成功");
                checkConnectionByFID(bean.fID);
            }
        }
    }

    @Override
    public void showBottomLayout(boolean isShow) {
        if (!isShow) {
            if (isAttachView()) getMvpView().showBottomLayout();
        }
    }

    @Override
    public void hiddenBottomLayout(boolean isShow) {
        // 如果是显示状态就隐藏
        if (isShow) {
            if (isAttachView()) getMvpView().hiddenBottomLayout();
        }
    }

    @Override
    public void checkSkipAnim(boolean isFirst) {
        boolean animSet = themeModel.getAnimSet();
        if (isFirst) {
            // 表示是在initView中初始化，不需要跳转
            if (animSet) {
                if (isAttachView()) getMvpView().firstNotUseAnim();
            } else {
                if (isAttachView()) getMvpView().firstUseAnim();
            }
        } else {
            if (animSet) {
                if (isAttachView()) getMvpView().notUseAnim();
            } else {
                if (isAttachView()) getMvpView().useAnim();
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (isAttachView()) getMvpView().dismissDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                if (isAttachView()) getMvpView().setLatLonPoint(address.getLatLonPoint());
            } else {
                Toaster.makeText(R.string.map_error_data);
            }
        } else {
            Toaster.makeText(R.string.map_error_data);
        }
    }

}
