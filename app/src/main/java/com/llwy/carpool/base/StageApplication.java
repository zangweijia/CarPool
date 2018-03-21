package com.llwy.carpool.base;

import android.app.Application;

import com.llwy.carpool.BuildConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import org.xutils.x;


/**
 * Created by ZWJ on 2018/3/9.
 */

public class StageApplication extends Application {

    private static StageApplication instance;
    private ApplicationLike tinkerApplicationLike;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        initThinker();
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                instance);
        config.memoryCacheExtraOptions(480, 800);
        config.diskCacheExtraOptions(480, 800, null);
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        if (BuildConfig.DEBUG) {
            config.writeDebugLogs(); // Remove for release app
        }
        ImageLoader.getInstance().init(config.build());
    }

    public static StageApplication getInstance() {
        return instance;
    }


    private void initThinker() {
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(3);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }


}