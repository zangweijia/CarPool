package com.llwy.carpool.presenter;

import com.llwy.carpool.base.AppClient;
import com.llwy.carpool.base.BasePresenter;
import com.llwy.carpool.base.SubscriberCallBack;
import com.llwy.carpool.model.News;
import com.llwy.carpool.view.IBaseDetailsView;

import java.util.List;

/**
 * Created by ZWJ on 2018/3/12.
 */

public class BaseDetailPresenter extends BasePresenter<IBaseDetailsView> {
    public BaseDetailPresenter(IBaseDetailsView mvpView) {
        super(mvpView);
    }

    public void getNews(String ste){
        addSubscription(AppClient.getApiService().getNews( ), new SubscriberCallBack<List<News>>() {
            @Override
            protected void onSuccess(List<News> response) {
                mvpView.onGetNewsDetails(response);
            }

        });
    }




}
