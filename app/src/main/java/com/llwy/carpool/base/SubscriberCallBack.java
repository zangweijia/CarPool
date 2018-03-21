package com.llwy.carpool.base;

import android.text.TextUtils;

import com.llwy.carpool.utils.ToastUtils;


/**
 * Created by Administrator
 * on 2016/5/18.
 */
public abstract class SubscriberCallBack<T> extends BaseCallBack<ResultResponse<T>> {


    @Override
    public void onNext(ResultResponse response) {
        boolean isSuccess =  !TextUtils.isEmpty(response.Result) && response.Result.equals("1");
        if (isSuccess) {
            onSuccess((T) response.Data);
        } else {
            ToastUtils.showToast(response.Message);
            onFailure(response);
        }
    }

    protected abstract void onSuccess(T response);
}
