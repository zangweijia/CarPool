package com.llwy.carpool.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.llwy.carpool.R;
import com.llwy.carpool.listener.RefreshListener;
import com.llwy.carpool.utils.AppManager;
import com.llwy.carpool.utils.StatusBarCompat;
import com.llwy.carpool.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends AppCompatActivity {
    protected Context ct;
    private boolean isActive;
    protected View emptyview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
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
        onUnsubscribe();
    }

    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
            Log.i("ACTIVITY", "程序进入后台");
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
        ToastUtils.showToast(msg);
    }

    protected abstract void initView();

    protected abstract void initData();

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

    public void onPause() {
        super.onPause();
        onUnsubscribe();
    }

    private CompositeSubscription mCompositeSubscription;

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        XutilsHttp.getInstance().Cancle();
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


    protected void addOnScrollListener(int layoutId, final RefreshListener refreshListener) {

        RefreshLayout mRefreshLayout = findViewById(layoutId); //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshListener.OnRefreshListener(refreshlayout);
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshListener.OnLoadmoreListener(refreshlayout);
            }
        });


//        <com.scwang.smartrefresh.layout.SmartRefreshLayout
//        android:id="@+id/refreshLayout"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        app:srlAccentColor="#00000000"
//        app:srlEnablePreviewInEditMode="true"
//        app:srlPrimaryColor="#00000000">
//
//
//        <com.scwang.smartrefresh.layout.header.ClassicsHeader
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content" />
//
//        <TextView
//        android:id="@+id/tv_detaiils"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:background="#ff5400"
//        android:text="测试" />
//
//        <com.scwang.smartrefresh.layout.footer.ClassicsFooter
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content" />
//
//
//    </com.scwang.smartrefresh.layout.SmartRefreshLayout>


    }


}
