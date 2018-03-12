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

import com.llwy.llwystage.R;
import com.llwy.llwystage.base.BaseActivity;
import com.llwy.llwystage.base.Constants;
import com.llwy.llwystage.utils.ImageLoaderUtils;
import com.llwy.llwystage.utils.RxCountDown;
import com.llwy.llwystage.utils.SignUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class SplashActivity extends BaseActivity {

    final int COUT_DOWN_TIME = 5;
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

//        addSubscription(AppClient.getApiService().getNews(), new SubscriberCallBack<List<News>>() {
//            @Override
//            protected void onSuccess(List<News> response) {
//                showToast(response.get(0).getCName().toString());
//            }
//        });
//
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

        forward(MainActivity.class);
        FinishAct();
    }


}
