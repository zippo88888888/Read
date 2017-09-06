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

    public final static String SEARCH_FRAGMENT_TAG = "fragment_search";

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
        int color;
        if (Content.THEME_RED.equals(theme)) {
            color = R.color.red;
        } else if (Content.THEME_PINK.equals(theme)) {
            color = R.color.pink;
        } else if (Content.THEME_VIOLET.equals(theme)) {
            color = R.color.violet;
        } else if (Content.THEME_BLUE.equals(theme)) {
            color = R.color.blue;
        } else if (Content.THEME_GREEN.equals(theme)) {
            color = R.color.green;
        } else if (Content.THEME_BLACK.equals(theme)) {
            color = R.color.black;
        } else {
            color = R.color.red;
        }
        return color;
    }

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

    public final static int STATUE_TRANSPARENT = 1;
    public final static int STATUE_NORMAL = 2;

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
