package com.llwy.carpool.presenter;

import com.llwy.carpool.base.ApiService;
import com.llwy.carpool.base.AppClient;
import com.llwy.carpool.base.BasePresenter;
import com.llwy.carpool.base.SubscriberCallBack;
import com.llwy.carpool.model.UserBean;
import com.llwy.carpool.view.ILoginView;

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
