package com.eren.snowframe.ui.home;

import android.os.Bundle;
import android.widget.Button;

import com.eren.snowframe.R;
import com.eren.snowframe.base.fragment.BaseMvpFragment;
import com.eren.snowframe.bean.BannerBean;
import com.eren.snowframe.contract.TestContract;
import com.eren.snowframe.model.TestModel;
import com.eren.snowframe.presenter.TestPresenter;
import com.eren.snowframe.ui.SecondActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseMvpFragment<TestPresenter, TestModel> implements TestContract.View {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void initData() {

    }

    @Override
    protected void initPresenter() {
        mPresenter.setMV(mModel, this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void bannerList(List<BannerBean> bannerList) {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        startActivity(SecondActivity.class);
    }
}
