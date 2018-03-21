package com.llwy.llwystage.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.llwy.llwystage.R;
import com.llwy.llwystage.base.BaseActivity;
import com.llwy.llwystage.base.Constants;
import com.llwy.llwystage.presenter.LoginPresenter;
import com.llwy.llwystage.utils.ImageLoaderUtils;
import com.llwy.llwystage.utils.RxCountDown;
import com.llwy.llwystage.utils.SignUtil;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class SplashActivity extends BaseActivity {

    final int COUT_DOWN_TIME = 3;
    private Subscription mSubscription;


    @BindView(R.id.banner_view)
    ImageView mBannerView;
    @BindView(R.id.splash_view)
    ImageView mSplashView;
    @BindView(R.id.skip_real)
    TextView mSkipReal;
    @BindView(R.id.guide_fragment)
    FrameLayout mGuideFragment;
    @BindView(R.id.ad_click_small)
    ImageView mAdClickSmall;
    @BindView(R.id.ad_click)
    LinearLayout mAdClick;
    @BindView(R.id.ad_ignore)
    FrameLayout mAdIgnore;
    @BindView(R.id.splash_video_frame)
    FrameLayout mSplashVideoFrame;
    @BindView(R.id.splash_video_layout)
    RelativeLayout mSplashVideoLayout;

    @Override
    protected void initView() {
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
//        LoadingDailog.Builder loadBuilder =
//                new LoadingDailog.Builder(ct)
//                        .setMessage("加载中...")
//                        .setCancelable(true)
//                        .setCancelOutside(true);
//        LoadingDailog dialog = loadBuilder.create();
//        dialog.show();


        mSubscription = RxCountDown.countDown(COUT_DOWN_TIME)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        ImageLoaderUtils.displayBigImage(Constants.SplashImgUrl, mSplashView);
                        mAdClickSmall.setVisibility(View.VISIBLE);
                        mSplashView.setVisibility(View.VISIBLE);
                        mAdIgnore.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        goMain();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSkipReal.setText(TextUtils.concat(integer.intValue() + "s", getResources().getString(R.string.splash_ad_ignore)));
                    }
                });

    }

    private void goMain() {

        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();

        forward(LoginActivity.class);
        FinishAct();

//        VersionUpdateConfig.getInstance()//获取配置实例
//                .setContext(this)//设置上下文
//                .setDownLoadURL("http://api.chuanchebao.com/down/android.apk")//设置文件下载链接
//                .setNewVersion("1.0.3")//设置即将下载的APK的版本号,避免重复下载
//                //  .setFileSavePath(savePath)//设置文件保存路径（可不设置）
//                .setNotificationIconRes(R.mipmap.ic_launcher)//设置通知图标
//                .setNotificationSmallIconRes(R.mipmap.ic_launcher)//设置通知小图标
//                .setNotificationTitle("版本升级Demo")//设置通知标题q
//                .startDownLoad();//开始下载
    }

}
