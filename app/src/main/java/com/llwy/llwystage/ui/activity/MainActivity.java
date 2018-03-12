package com.llwy.llwystage.ui.activity;

import com.llwy.llwystage.R;
import com.llwy.llwystage.base.BaseActivity;
import com.llwy.llwystage.utils.StatusBarCompat;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity {


    @Override
    protected void initView() {
//        StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {

    }



}
