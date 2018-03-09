package com.llwy.llwystage.base;

import android.text.TextUtils;

import com.llwy.llwystage.base.BaseCallBack;
import com.llwy.llwystage.base.ResultResponse;
import com.llwy.llwystage.utils.ToastUtils;


/**
 * Created by Administrator
 * on 2016/5/18.
 */
public abstract class SubscriberCallBack<T> extends BaseCallBack<ResultResponse<T>> {


    @Override
    public void onNext(ResultResponse response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.Message) && response.Message.equals("加载成功!"))
                || !TextUtils.isEmpty(response.Result) && response.Result.equals("1");
        if (isSuccess) {
            onSuccess((T) response.Data);
        } else {
            ToastUtils.showToast(response.Message);
            onFailure(response);
        }
    }

    protected abstract void onSuccess(T response);
}