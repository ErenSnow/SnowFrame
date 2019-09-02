package com.eren.snowframe.ui;

import com.eren.snowframe.R;
import com.eren.snowframe.base.activity.BaseMvpActivity;
import com.eren.snowframe.bean.BannerBean;
import com.eren.snowframe.contract.TestContract;
import com.eren.snowframe.model.TestModel;
import com.eren.snowframe.presenter.TestPresenter;
import com.eren.snowframe.widget.status.StatusBarTextUtil;
import com.eren.snowframe.widget.status.StatusBarUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

public class SecondActivity extends BaseMvpActivity<TestPresenter, TestModel> implements TestContract.View {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView() {
        setTitle("测试 Activity");
        // 设置布局向下移动，防止嵌入到状态栏中
        StatusBarUtil.setMargin(this, toolBar);
        // 设置状态栏字体颜色为深色（黑色）
        StatusBarTextUtil.setAndroidNativeLightStatusBar(this, true);
    }

    @Override
    protected void initData() {
        mPresenter.getBannerList();
    }

    @Override
    public void bannerList(List<BannerBean> bannerList) {
        for (BannerBean bannerBean : bannerList) {
            Logger.d("Test:" + bannerBean.getTitle());
        }
    }
}
