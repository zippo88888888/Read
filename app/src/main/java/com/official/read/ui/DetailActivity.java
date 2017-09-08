package com.official.read.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.content.DisposeManager;
import com.official.read.content.bean.DetailBean;
import com.official.read.dialog.ShareDialog;
import com.official.read.presenter.DetailPresenterImpl;
import com.official.read.util.GlideImageLoader;
import com.official.read.util.Toaster;
import com.official.read.util.anim.AnimUtil;
import com.official.read.util.anim.EasyTransition;
import com.official.read.view.DetailView;
import com.official.read.content.listener.AppBarChangeListener;
import com.official.read.weight.MyGridView;
import com.official.read.weight.MyNestedScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

public class DetailActivity extends BaseActivity<DetailPresenterImpl, DetailView> implements
        DetailView, MyNestedScrollView.ScrollChangeListener {

    AppBarLayout appBarLayout;

    Toolbar toolbar;
    TextView title;
    CollapsingToolbarLayout toolbarLayout;

    FloatingActionButton actionButton;

    MyNestedScrollView scrollView;
    LinearLayout detailRootLayout;

    /** 地图 */
    ImageView mapView;

    Banner banner;

    RelativeLayout houseTimeLayout;

    /** 房源亮点 */
    MyGridView light_GridView;

    // 左一文字说明，右一文字说明
    TextView bidPriceTxt,qpPriceTxt;

    // 成交价，起拍价，保证金
    TextView bidPrice, qpPrice, marginPrice;
    // 房源标题，房源编号，房源时间
    TextView houseTitle,houseNo, house_time;
    // 房屋类型，建筑面积，房屋楼层
    TextView houseType,houseArea,houseFloor;
    // 优先购买人，房屋租约，房屋地址
    TextView housePriority,houseLease,houseAddress;

    // 房源介绍-------------------------

    // 房源户型，房屋租约
    TextView houseType2,houseLease2;
    // 看房情况，房屋楼层，房屋年限
    TextView houseLook,houseFloor2,houseAgeLimit;
    // 房屋朝向，房屋类型，装修情况
    TextView houseOrientation,houseHouseType3,houseFitment;

    // 看房记录
    TextView look_record;

    // 隐藏的数据
    LinearLayout house_info_hiddenLayout;
    // 默认隐藏
    private boolean isHidden = true;
    // 隐藏的数据操作Layout
    LinearLayout house_info_hidden_textLayout;
    // 文字说明
    TextView house_info_hidden_text;

    LinearLayout bottomLayout;

    DetailBean bean;

    @Override
    protected int getContentView() {
        isSetBar = false;
        return R.layout.activity_detail;
    }

    @Override
    protected DetailPresenterImpl initPresenter() {
        return new DetailPresenterImpl();
    }

    @Override
    protected void initView() {
        appBarLayout = $(R.id.new_home_list_item_pic);
        scrollView = $(R.id.detailScrollView);
        scrollView.setScrollChangeListener(this);
        toolbarLayout = $(R.id.detailToolbarLayout);
        toolbar = $(R.id.detail_Toolbar);
        title = $(R.id.tool_bar_title);
        toolbar.setNavigationOnClickListener(this);

        detailRootLayout = $(R.id.detailRootLayout);

        actionButton = $(R.id.snackBar_FButton);
        actionButton.setOnClickListener(this);

        dynamicAddView(toolbarLayout, "toolbarLayoutAttr", R.color.baseColor);
        dynamicAddView(actionButton, "actionButtonAttr", R.color.baseColor);

        $(R.id.detail_collection).setOnClickListener(this);
        $(R.id.detail_tel).setOnClickListener(this);

        banner = $(R.id.house_info_Banner);

        look_record = $(R.id.house_info_look_record);       // 记录文字说明
        $(R.id.house_info_record).setOnClickListener(this); // 记录
        $(R.id.house_info_rule).setOnClickListener(this);   // 规则
        $(R.id.house_info_notice).setOnClickListener(this); // 公告

        houseTimeLayout = $(R.id.house_info_titleLayout);
        light_GridView = $(R.id.house_info_light_GridView);

        $(R.id.house_info_mapInfo).setOnClickListener(this); // 地图
        mapView = $(R.id.house_info_mapView);
        mapView.setOnClickListener(this);

        bidPriceTxt = $(R.id.house_info_qpPriceTxt);
        qpPriceTxt = $(R.id.house_info_bidPrice_txt);
        qpPrice = $(R.id.house_info_bidPrice);
        bidPrice = $(R.id.house_info_qpPrice);

        marginPrice = $(R.id.house_info_margin);

        house_time = $(R.id.house_info_timeEnd);
        houseTitle = $(R.id.house_info_title);
        houseNo = $(R.id.house_info_no);
        houseType = $(R.id.house_info_type);
        houseArea = $(R.id.house_info_area);
        houseFloor = $(R.id.house_info_floor);
        housePriority = $(R.id.house_info_priority);
        houseLease = $(R.id.house_info_lease);
        houseAddress = $(R.id.house_info_address);
        houseType2 = $(R.id.house_info_houseType);
        houseLease2 = $(R.id.house_info_lease2);
        houseLook = $(R.id.house_info_condition);
        houseFloor2 = $(R.id.house_info_floor2);
        houseAgeLimit = $(R.id.house_info_ageLimit);
        houseOrientation = $(R.id.house_info_orientation);
        houseHouseType3 = $(R.id.house_info_houseType2);
        houseFitment = $(R.id.house_info_fitment);

        // 隐藏的数据
        house_info_hiddenLayout = $(R.id.house_info_hiddenLayout);
        // 隐藏的数据操作Layout
        house_info_hidden_textLayout = $(R.id.house_info_hidden_textLayout);
        house_info_hidden_textLayout.setOnClickListener(this);
        // 文字说明
        house_info_hidden_text = $(R.id.house_info_hidden_text);

        bottomLayout = $(R.id.detail_bottomLayout);

        presenter.checkSkipAnim(true);
    }

    @Override
    protected void initData() {
        String fID = getIntent().getStringExtra("fID");
        presenter.getDetailData(fID);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.detail_collection:
                presenter.checkDetailBean(bean, 1);
                break;
            case R.id.detail_tel:
                presenter.checkDetailBean(bean, 2);
                break;
            case R.id.house_info_hidden_textLayout: {
                presenter.isShowOrHidden(isHidden);
                break;
            }
            case R.id.house_info_mapView:
            case R.id.house_info_mapInfo:
            case R.id.house_info_record:
                Toaster.makeText("BUILDING...");
                break;
            case R.id.house_info_rule: {
                ArrayMap<String, Object> map = new ArrayMap<>();
                map.put("url", bean.auction_des_url);
                jumpActivity(map, WebViewActivity.class);
                break;
            }
            case R.id.house_info_notice: {
                ArrayMap<String, Object> map = new ArrayMap<>();
                map.put("url", bean.auction_info_url);
                jumpActivity(map, WebViewActivity.class);
                break;
            }
            case R.id.snackBar_FButton: {
                ShareDialog dialog = new ShareDialog();
                dialog.setDialogItemClickListener(this);
                dialog.show(getSupportFragmentManager(), Content.DIALOG_SHARE_TAG);
                break;
            }
            default:
                back();
                break;
        }
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
    public void initDetailData(DetailBean bean) {
        this.bean = bean;

        presenter.checkConnectionByFID(bean.house_id);

        title.setText(bean.house_name);

        presenter.checkState(bean);
        houseTitle.setText(bean.house_name);
        houseNo.setText("房源编号：" + bean.numbers);

        presenter.checkTop3Value(bean.deposit, bean.source, bean.preemption_claimant);

        houseArea.setText(bean.house_area + "㎡");
        houseFloor.setText(bean.floor_name);

        houseLease.setText(bean.is_rent);
        houseAddress.setText(bean.detail_address);
        // 房源介绍
        houseType2.setText(bean.house_type);
        houseLease2.setText(bean.is_rent);
        houseLook.setText(bean.canfield);
        houseFloor2.setText(bean.floor_name);
        houseAgeLimit.setText(bean.limits);
        houseOrientation.setText(bean.towards);
        houseHouseType3.setText(bean.build);
        houseFitment.setText(bean.equipment);

        look_record.setText("近7天新增" + bean.auction_7day_count + "位看房客户，共" + bean.auction_total + "位");

        List<DetailBean.HouseInfoImage> imgs = bean.imgs;
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgs);
        banner.isAutoPlay(false);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();

        presenter.checkSkipAnim(false);

        appBarLayout.addOnOffsetChangedListener(appBarChangeListener);
        presenter.setLightGridViewData(bean.tag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DisposeManager.getInstance().cancel(Content.DISPOSABLE_DETAIL_DATA);
    }

    @Override
    public void tel() {
        String phoneNum = "tel:" + bean.telphone;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum));
        startActivity(intent);
    }

    @Override
    public void show() {
        house_info_hiddenLayout.setVisibility(View.VISIBLE);
        house_info_hidden_text.setText("收起");
        isHidden = false;
    }

    @Override
    public void hidden() {
        house_info_hiddenLayout.setVisibility(View.GONE);
        house_info_hidden_text.setText("展开");
        isHidden = true;
    }

    @Override
    public void setLightGridViewData(final List<String> data) {
        //I don't want to do this
        light_GridView.setVisibility(View.GONE);
//        light_GridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenLightGridView() {
        light_GridView.setVisibility(View.GONE);
    }

    @Override
    public void setTop3Value(String deposit, String source, String priority) {
        marginPrice.setText(deposit);
        houseType.setText(source);
        housePriority.setText(priority);
    }

    @Override
    public void setValue(String bidPrice_e, String bidPriceT, String qpPrice_e, String qpPriceT, String houseTime_e) {
        bidPrice.setText(bidPrice_e);
        bidPriceTxt.setText(bidPriceT);
        qpPrice.setText(qpPrice_e);
        qpPriceTxt.setText(qpPriceT);
        house_time.setText(houseTime_e);
    }

    @Override
    public void setConnectionState(String state) {
        ((TextView) $(R.id.detail_collection)).setText(state);
    }

    @Override
    public Context getBaseViewContext() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.checkPermission2(requestCode, grantResults);
    }

    @Override
    public void dialogClick(View view, Object obj) {
        int id = (int) obj;
        switch (id) {
            case R.id.share_qq:
            case R.id.share_weibo:
            case R.id.share_webChat:
                Toaster.makeText("BUILDING...");
                break;
            case R.id.share_more:
                String msg = "这是分享内容";
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, msg);
                startActivity(Intent.createChooser(textIntent, "选择更多"));
                break;
        }
    }

    boolean isShow = true;

    @Override
    public void scrollToUp() {
        // 隐藏
        presenter.hiddenBottomLayout(isShow);
    }

    @Override
    public void scrollToDown() {
        // 显示
        presenter.showBottomLayout(isShow);
    }

    @Override
    public void showBottomLayout() {
        isShow = true;
        AnimUtil.setToUpShowForDetail(this, bottomLayout, false);
    }

    @Override
    public void hiddenBottomLayout() {
        isShow = false;
        AnimUtil.setToBottomHiddenForDetail(this, bottomLayout);
    }

    @Override
    public void firstUseAnim() {
        AnimUtil.setToBottomHiddenForDetail(DetailActivity.this, bottomLayout);
    }

    @Override
    public void firstNotUseAnim() {
        bottomLayout.setVisibility(View.VISIBLE);
    }

    AppBarChangeListener appBarChangeListener = new AppBarChangeListener() {
        @Override
        public void changeToolBarTitleAlpha(float alpha) {
            title.setAlpha(alpha);
        }

        @Override
        public void changeTitleAlpha(float alpha) {
            houseTitle.setAlpha(alpha);
            house_time.setAlpha(alpha);
        }
    };

    @Override
    public void useAnim() {
        EasyTransition.enter(this, 500, new DecelerateInterpolator(), new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                AnimUtil.setStartActionButtonForDetail(actionButton);
                AnimUtil.setStartRootViewForDetail(detailRootLayout);
                AnimUtil.setToUpShowForDetail(DetailActivity.this, bottomLayout, true);
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void notUseAnim() {
        actionButton.setVisibility(View.VISIBLE);
        detailRootLayout.setVisibility(View.VISIBLE);
    }
}
