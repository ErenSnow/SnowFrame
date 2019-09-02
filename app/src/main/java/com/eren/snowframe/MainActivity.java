package com.eren.snowframe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eren.snowframe.ui.find.FindFragment;
import com.eren.snowframe.ui.home.HomeFragment;
import com.eren.snowframe.ui.ip.IpFragment;
import com.eren.snowframe.ui.mine.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Eren
 * <p>
 * 主界面
 */
public class MainActivity extends SupportActivity {

    public static final int HOME = 0;
    public static final int FIND = 1;
    public static final int IP = 2;
    public static final int MINE = 3;

    @BindView(R.id.bnv_bar)
    BottomNavigationView bottomNavigationView;

    private SupportFragment[] fragments = new SupportFragment[5];

    /**
     * 用来计算返回键的点击间隔时间
     */
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //权限申请
        requestPermissions();
        initFragment(savedInstanceState);
        initView();
    }

    /**
     * 权限申请
     */
    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Logger.d("Permissions" + permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Logger.d("Permissions" + permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Logger.d("Permissions" + permission.name + " is denied.");
                        }
                    }
                });
    }

    /**
     * 初始化Fragment
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragments[HOME] = new HomeFragment();
            fragments[FIND] = new FindFragment();
            fragments[IP] = new IpFragment();
            fragments[MINE] = new MineFragment();

            loadMultipleRootFragment(R.id.fl_container, HOME,
                    fragments[HOME],
                    fragments[FIND],
                    fragments[IP],
                    fragments[MINE]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            fragments[HOME] = findFragment(HomeFragment.class);
            fragments[FIND] = findFragment(FindFragment.class);
            fragments[IP] = findFragment(IpFragment.class);
            fragments[MINE] = findFragment(MineFragment.class);
        }
    }

    private void initView() {
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 重置到默认不选中图片
                resetToDefaultIcon();
                switch (item.getItemId()) {
                    case R.id.menu_item_active:
                        item.setIcon(R.drawable.ic_nav_active_sel);
                        showHideFragment(fragments[HOME]);
                        break;
                    case R.id.menu_item_find:
                        item.setIcon(R.drawable.ic_nav_find_sel);
                        showHideFragment(fragments[FIND]);
                        break;
                    case R.id.menu_item_ip:
                        item.setIcon(R.drawable.ic_nav_ip_sel);
                        showHideFragment(fragments[IP]);
                        break;
                    case R.id.menu_item_mine:
                        item.setIcon(R.drawable.ic_nav_mine_sel);
                        showHideFragment(fragments[MINE]);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void resetToDefaultIcon() {
        MenuItem home = bottomNavigationView.getMenu().findItem(R.id.menu_item_active);
        home.setIcon(R.drawable.ic_nav_active_nor);
        MenuItem find = bottomNavigationView.getMenu().findItem(R.id.menu_item_find);
        find.setIcon(R.drawable.ic_nav_find_nor);
        MenuItem ip = bottomNavigationView.getMenu().findItem(R.id.menu_item_ip);
        ip.setIcon(R.drawable.ic_nav_ip_nor);
        MenuItem mine = bottomNavigationView.getMenu().findItem(R.id.menu_item_mine);
        mine.setIcon(R.drawable.ic_nav_mine_nor);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}