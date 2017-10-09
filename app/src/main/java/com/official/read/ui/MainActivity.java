package com.official.read.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.adapter.HomeFragmentAdapter;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.content.DisposeManager;
import com.official.read.presenter.MainPresenterImpl;
import com.official.read.util.AndroidUtil;
import com.official.read.util.Toaster;
import com.official.read.view.MainView;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenterImpl, MainView> implements MainView,
        NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TabLayout tabLayout;

    ViewPager viewPager;
    HomeFragmentAdapter vpAdapter;

    TextView header_title;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_search, menu);
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        drawerLayout = $(R.id.navigation_DrawerLayout);
        navigationView = $(R.id.navigation_view);
        tabLayout = $(R.id.sliding_tabs);
        viewPager = $(R.id.allVP);
        $(R.id.snackBar_FButton).setOnClickListener(this);
        $(R.id.snackBar_FButton).setVisibility(View.GONE);

        // 初始化toolbar
        setTitle(R.string.app_name);
        setNavigationIcon(R.drawable.menu);
        setToolbarInMenu();
        setOnMenuItemClickListener(this);
        setNavigationIconClickListener(this);

        // 初始化 DrawerLayout
        View view = navigationView.inflateHeaderView(R.layout.navigation_header_layout);
        header_title = (TextView) view.findViewById(R.id.navigation_title);
        header_title.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        AndroidUtil.disableNavigationViewScrollbars(navigationView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter.initVPData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.snackBar_FButton: {
                Toaster.makeText("add");
                break;
            }
            case R.id.navigation_title: {
                break;
            }
            default:
                drawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new SearchFragment(), Content.SEARCH_FRAGMENT_TAG)
                .addToBackStack("fragment:reveal")
                .commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(Content.SEARCH_FRAGMENT_TAG);
        presenter.checkFragment(fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_item_collection:
                jumpActivity(CollectActivity.class);
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.navigation_item_theme:
                jumpActivity(ThemeActivity.class);
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.navigation_item_set:
                jumpActivity(SetActivity.class);
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.navigation_item_egg:
                presenter.checkEgg();
                break;
            case R.id.navigation_item_about:
                jumpActivity(AboutActivity.class);
                drawerLayout.closeDrawer(Gravity.START);
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
    public void initMainVPData(List<Fragment> data) {
        vpAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), Content.TITLE, data);
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void finishActivity() {
        DisposeManager.getInstance().cancelAll();
        DisposeManager.getInstance().clear();
        finish();
    }

    @Override
    public void openEgg() {
        jumpActivity(EasterEggActivity.class);
    }
}
