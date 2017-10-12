package com.official.read.content;


import com.official.read.BuildConfig;
import com.official.read.R;
import com.official.read.content.bean.JusticeBean;
import com.official.read.util.Toaster;

import java.util.List;
import java.util.Map;

public class Content {

    public static final String FILE_PROVIDER = "com.official.read.fileProvider";

    public static final String[] TITLE = Toaster.getContext().getResources().getStringArray(R.array.titles);

    public static final String[] COLORS = Toaster.getContext().getResources().getStringArray(R.array.colors);

    public static final String HOT_HP = Toaster.getContext().getResources().getString(R.string.hot_hp);
    public static final String HOT_JA = Toaster.getContext().getResources().getString(R.string.hot_ja);
    public static final String HOT_PD = Toaster.getContext().getResources().getString(R.string.hot_pd);
    public static final String HOT_XH = Toaster.getContext().getResources().getString(R.string.hot_xh);

    public static final String SEARCH_FRAGMENT_TAG = "fragment_search";

    public static final String LUCK_DEFAULT_PWD = "####";

    /** 第一次输入密码 */
    public static final int LOCK_STATE_FIRST = 1;
    /** 清除密码 */
    public static final int LOCK_STATE_CLEAR = 2;
    /** 检查输入密码 */
    public static final int LOCK_STATE_CHECK = -1;

    /** 去设置密码Code */
    public static final int SET_LOCK_PWD_REQUEST_CODE = 10010;

    /** 设置密码成功Code */
    public static final int SET_LOCK_PWD_RESULT_CODE = 10011;

    /** 去清除密码Code */
    public static final int CLEAR_LOCK_PWD_REQUEST_CODE = 10012;

    /** 清除密码成功Code */
    public static final int CLEAR_LOCK_PWD_RESULT_CODE = 10013;

    // GDMap ---------------------------------------------------------------------------------------------------

    /**
     * 讯飞APP_ID
     * 请替换你自己的AppId，地址：http://www.xfyun.cn/
     */
    public static final String APP_ID = BuildConfig.APP_ID;

    /**
     * 腾讯 bugly ID
     */
    public static final String BUGLY_ID = BuildConfig.BUGLY_ID;

    /**
     * 默认的当前定位位置
     */
    public static final double NO_LAT_LON_POINT_DATA = -1;

    /**
     * 驾车模式
     */
    public static final int NAVIGATE_CAR = 1;
    /**
     * 步行模式
     */
    public static final int NAVIGATE_WALK = 2;
    /**
     * 骑行模式
     */
    public static final int NAVIGATE_CYCLING = 3;

    public static final String MARKER_KEY_METRO = "地铁";
    public static final String MARKER_KEY_BUS = "公交";
    public static final String MARKER_KEY_BANK = "银行";
    public static final String MARKER_KEY_MEDICAL = "医疗";
    public static final String MARKER_KEY_EDUCATION = "教育";
    public static final String MARKER_KEY_EAT = "餐饮";
    public static final String MARKER_KEY_SHOPPING = "购物";

    public static final int[] MARKERS = {
            R.drawable.poi_marker_1,
            R.drawable.poi_marker_2,
            R.drawable.poi_marker_3,
            R.drawable.poi_marker_4,
            R.drawable.poi_marker_5,
            R.drawable.poi_marker_6,
            R.drawable.poi_marker_7,
            R.drawable.poi_marker_8,
            R.drawable.poi_marker_9,
            R.drawable.poi_marker_10
    };

    /**
     * 主题
     */
    public static final String THEME_RED = "red_skin.skin";
    public static final String THEME_PINK = "pink_skin.skin";
    public static final String THEME_VIOLET = "violet_skin.skin";
    public static final String THEME_BLUE = "blue_skin.skin";
    public static final String THEME_GREEN = "green_skin.skin";
    public static final String THEME_BLACK = "black_skin.skin";

    public static int getBaseColorByTheme(String theme) {
        if (Content.THEME_RED.equals(theme)) {
            return R.color.red;
        } else if (Content.THEME_PINK.equals(theme)) {
            return R.color.pink;
        } else if (Content.THEME_VIOLET.equals(theme)) {
            return R.color.violet;
        } else if (Content.THEME_BLUE.equals(theme)) {
            return R.color.blue;
        } else if (Content.THEME_GREEN.equals(theme)) {
            return R.color.green;
        } else if (Content.THEME_BLACK.equals(theme)) {
            return R.color.black;
        } else {
            return R.color.red;
        }
    }

    /**
     * 刷新状态
     */
    public static final int REFRESH = 0;

    /**
     * 加载更多状态
     */
    public static final int LOADMORE = 1;

    /**
     * 是否是正式版
     */
    public static final boolean IS_OFFICIAL = BuildConfig.IS_OFFICIAL;

    /**
     * 每页显示的个数
     */
    public static final int PAGE_COUNT = 6;

    private static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String PATH = BuildConfig.PATH + BASE_URL;

    public static final int STATUE_TRANSPARENT = 1;
    public static final int STATUE_NORMAL = 2;

    /**
     * 创建连接
     */
    public static Map<String, Object> createMapForUrl(Map<String, Object> map) {
        if (map == null) {
            throw new NullPointerException("Map is not null");
        }
        map.put("os_api", "22");
        map.put("device_type", "MI");
        map.put("device_platform", "android");
        map.put("ssmix", "a");
        map.put("manifest_version_code", "232");
        map.put("dpi", "400");
        map.put("abflag", "0");
        map.put("uuid", "651384659521356");
        map.put("version_code", "232");
        map.put("app_name", "tuchong");
        map.put("version_name", "2.3.2");
        map.put("openudid", "65143269dafd1f3a5");
        map.put("resolution", "1280*1000");
        map.put("os_version", ".8.1");
        map.put("ac", "wifi");
        map.put("aid", "0");
        return map;
    }

    public final static String URL1 = "http://pic.qiantucdn.com/58pic/18/10/55/55d34a6150297_1024.jpg!/fw/" +
            "780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center";

    public final static String URL2 = "http://pic.qiantucdn.com/58pic/11/21/22/14C58PIC4Ut.jpg!/fw/780/watermark" +
            "/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center";

    // Dialog Tag ------------------------------------------------------------------------------------

    /**
     *  JusticeFragment Dialog Tag
     */
    public static final String DIALOG_JUSTICE_TAG = "justiceDialog";

    /**
     *  HistoryFragment Dialog Tag
     */
    public static final String DIALOG_HISTORY_TAG = "historyDialog";

    /**
     *  DetailActivity Dialog Tag
     */
    public static final String DIALOG_SHARE_TAG = "shareDialog";

    /**
     *  AboutActivity Dialog Tag
     */
    public static final String DIALOG_TEL_TAG = "telDialog";

    /**
     * GDMapActivity Dialog Tag
     */
    public static final String DIALOG_MAP_SELECT_TAG = "mapSelectDialog";


    // Disposable ------------------------------------------------------------------------------------

    /**
     * RecommendFragment Disposable
     */
    public static final String DISPOSABLE_RECOMMEND_INIT_DATA = "recommend_initData";

    /**
     * JusticeFragment Disposable
     */
    public static final String DISPOSABLE_JUSTICE_INIT_DATA = "justice_initData";

    /**
     * HistoryFragment Disposable
     */
    public static final String DISPOSABLE_HISTORY_INIT_DATA = "history_initData";

    /**
     * ListActivity Disposable
     */
    public static final String DISPOSABLE_LIST_INIT_DATA = "list_initData";

    /**
     * DetailActivity Disposable
     */
    public static final String DISPOSABLE_DETAIL_DATA = "detail_initData";

}
