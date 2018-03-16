package com.llwy.llwystage.presenter;

import com.llwy.llwystage.base.ApiService;
import com.llwy.llwystage.base.AppClient;
import com.llwy.llwystage.base.BasePresenter;
import com.llwy.llwystage.base.ResultResponse;
import com.llwy.llwystage.base.SubscriberCallBack;
import com.llwy.llwystage.model.UserBean;
import com.llwy.llwystage.view.ILoginView;

import java.util.Map;

/**
 * Created by ZWJ on 2018/3/13.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView mvpView) {
        super(mvpView);
    }


    public void Login(Map<String,String> userBean) {
        addSubscription(AppClient.getApiService().Login(ApiService.API_SERVER_URL_NEW +"Login",userBean), new SubscriberCallBack<UserBean>() {

            @Override
            protected void onSuccess(UserBean response) {
                mvpView.showLogin(response);
            }
        });
    }

}
