package com.llwy.llwystage.base;

import android.app.Application;

import com.llwy.llwystage.BuildConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;


/**
 * Created by ZWJ on 2018/3/9.
 */

public class StageApplication extends Application {

    private static StageApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        x.Ext.init(this);

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

}