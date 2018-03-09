package com.llwy.llwystage.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.llwy.llwystage.R;
import com.llwy.llwystage.utils.AppManager;
import com.llwy.llwystage.utils.CustomToast;
import com.llwy.llwystage.utils.StatusBarCompat;


import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends FragmentActivity implements
        OnClickListener {
    protected Context ct;
    private boolean isActive;
    protected View emptyview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);

        StatusBarCompat.compat(this, getResources().getColor(R.color.blue));

        ct = this;
        emptyview = LayoutInflater.from(ct).inflate(R.layout.a_empty, null);
        initView();
        initData();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }



    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
            Log.i("ACTIVITY", "程序进入后台");

            putSharedPreferences("isHT", "1");

        }
        super.onStop();
    }

    /**
     * 页面跳转
     *
     * @param cls 目标画面
     */
    public <T> void forward(Class<T> cls) {
        forward(cls, new Intent());
    }

    /**
     * 页面跳转
     *
     * @param cls    目标画面
     * @param intent 数据传递
     */
    public <T> void forward(Class<T> cls, Intent intent) {
        intent.setClass(ct, cls);
        startActivity(intent);
    }

    /**
     * 页面关闭
     */
    public void FinishAct() {
        AppManager.getAppManager().finishActivity();
    }


    public void onResume() {
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Log.i("ACTIVITY", "程序从后台唤醒");
        }
        super.onResume();
    }


    /**
     * 判断是否锁屏
     */
    public boolean isScreenLocked() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        if (isScreenOn)
            return false;
        else
            return true;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }



    protected void showToast(String msg) {
        showToast(msg, 0);
    }

    protected void showToast(String msg, int time) {
        CustomToast customToast = new CustomToast(ct, msg, time);
        customToast.show();
    }



    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
    }


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void processClick(View v);

    /**
     * 设置首选项
     *
     * @param key   键
     * @param value 值
     */
    public void putSharedPreferences(String key, String value) {
        SharedPreferences.Editor shared = getSharedPreferences("KEY_PREFERENCE", 0).edit();
        shared.putString(key, value);
        shared.commit();
    }



    /**
     * 删除首选项
     *
     * @param key 键
     */
    public void removeSharedPreferences(String key) {
        SharedPreferences.Editor shared = getSharedPreferences("KEY_PREFERENCE", 0).edit();
        shared.remove(key);
        shared.commit();
    }

    /**
     * 获取本应用保存的首选项
     *
     * @param key 首选项KEY
     * @return 首选项
     */
    public String getSharedPreference(String key) {
        SharedPreferences sp = getSharedPreferences("KEY_PREFERENCE", 0);
        if (sp != null && sp.contains(key)) {
            return sp.getString(key, "");
        }
        return null;
    }


    public void showLog(String tag, String str) {
        Log.e(tag, str);
    }


    public boolean isNowUser(String user) {
        if (user.equals(getSharedPreference("userid"))) {
            return true;
        } else {
            return false;
        }
    }





    public void onPause() {
        super.onPause();
        onUnsubscribe();
    }

    private CompositeSubscription mCompositeSubscription;

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

}
