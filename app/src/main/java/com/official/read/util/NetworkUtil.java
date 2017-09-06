package com.official.read.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * com.official.read.util
 * Created by ZP on 2017/8/29.
 * description:
 * version: 1.0
 */

public class NetworkUtil {

    public static final int NET_WORK_2G = 0x2;
    public static final int NET_WORK_3G = 0x3;
    public static final int NET_WORK_4G = 0x4;
    public static final int NET_WORK_WIFI = 0x1;
    public static final int NET_WORK_ERROR = 0x0;

    private static final String TD_SCDMA = "TD-SCDMA";	// 中国移动3G
    private static final String WCDMA = "WCDMA";		// 中国联通3G
    private static final String CDMA2000 = "CDMA2000";	// 中国电信3G

    /**
     * 是否有可用的网络连接
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 获得当前网络的具体的状态
     * 1）通过广播获取 实时获取当前网络状态
     * 2）在Application中初始化
     * 3）在任意界面获取网络状态
     */
    public static int getNetWorkType(Context context) {
        int type = -1;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            int workType = info.getType();
            if (workType == ConnectivityManager.TYPE_WIFI) { // WIFI
                type = NET_WORK_WIFI;
            } else if (workType == ConnectivityManager.TYPE_MOBILE) { // MOBILE
                String subtypeName = info.getSubtypeName();
                L.e("Network subtypeName" + subtypeName);
                int subtype = info.getSubtype();
                switch (subtype) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
                        type = NET_WORK_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  // api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  // api<13 : replace by 15
                        type = NET_WORK_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    // api<11 : replace by 13
                        type = NET_WORK_4G;
                        break;
                    default:
                        if (subtypeName.equalsIgnoreCase(TD_SCDMA) || subtypeName.equalsIgnoreCase(WCDMA)
                                || subtypeName.equalsIgnoreCase(CDMA2000)) {
                            type = NET_WORK_3G;
                        } else {
                            L.e("当前网络格式:" + subtypeName);
                        }
                        break;
                }
                L.e("Network getSubtype : " + Integer.valueOf(subtype).toString());
            }
        } else {
            type = NET_WORK_ERROR;
            Toaster.makeText("当前没有网络连接");
        }
        return type;
    }

}
