package com.llwy.carpool.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llwy.carpool.R;
import com.llwy.carpool.base.BaseMvpFragment;
import com.llwy.carpool.presenter.HomePresenter;
import com.llwy.carpool.view.IHomeView;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements IHomeView {


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.a_empty,null);
    }

    @Override
    protected void bindViews(View view) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

    }
}
