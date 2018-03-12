package com.llwy.llwystage.presenter;

import com.llwy.llwystage.base.AppClient;
import com.llwy.llwystage.base.BasePresenter;
import com.llwy.llwystage.base.ResultResponse;
import com.llwy.llwystage.base.SubscriberCallBack;
import com.llwy.llwystage.model.News;
import com.llwy.llwystage.view.IBaseDetailsView;

import java.util.List;

/**
 * Created by ZWJ on 2018/3/12.
 */

public class BaseDetailPresenter extends BasePresenter<IBaseDetailsView> {
    public BaseDetailPresenter(IBaseDetailsView mvpView) {
        super(mvpView);
    }

    public void getNews(String ste){
        addSubscription(AppClient.getApiService().getNews(), new SubscriberCallBack<List<News>>() {
            @Override
            protected void onSuccess(List<News> response) {
                mvpView.onGetNewsDetails(response);
            }

            @Override
            protected void onFailure(ResultResponse response) {
                super.onFailure(response);

            }

            @Override
            protected void onError() {
                super.onError();


            }
        });
    }




}
