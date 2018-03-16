package com.llwy.llwystage.ui.activity;

import android.widget.Toast;

import com.llwy.llwystage.R;
import com.llwy.llwystage.base.BaseActivity;
import com.llwy.llwystage.base.Constants;
import com.llwy.llwystage.base.ResultResponse;
import com.llwy.llwystage.model.News;
import com.llwy.llwystage.model.UserBean;
import com.llwy.llwystage.presenter.BaseDetailPresenter;
import com.llwy.llwystage.presenter.LoginPresenter;
import com.llwy.llwystage.utils.SignUtil;
import com.llwy.llwystage.view.IBaseDetailsView;
import com.llwy.llwystage.view.ILoginView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZWJ on 2018/3/13.
 */

public class LoginActivity extends BaseActivity implements ILoginView, IBaseDetailsView {

    private LoginPresenter mPresenter;
    private BaseDetailPresenter baseDetailPresenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);

        baseDetailPresenter = new BaseDetailPresenter(this);


    }

    @Override
    protected void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", "154632121");
        map.put("PSW", "111111");
        map.put("Sign",SignUtil.GetSignNew(map));


        mPresenter.Login(map);
        baseDetailPresenter.getNews("");
    }

    @Override
    public void showLogin(UserBean userBean) {
        showToast(userBean.getPsw().toString() + "*******" + userBean.getUsername().toString());
    }

    @Override
    public void onGetNewsDetails(List<News> m) {
        Toast.makeText(ct, m.get(0).getCName(), Toast.LENGTH_LONG).show();
    }
}
