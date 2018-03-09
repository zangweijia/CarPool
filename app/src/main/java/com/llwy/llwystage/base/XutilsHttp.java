package com.llwy.llwystage.base;

import android.content.Context;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.Map;

public class XutilsHttp {

    private ImageOptions options;
    Callback.Cancelable utils;

    private XutilsHttp() {
    }

    /**
     * 单例模式
     *
     * @return
     */
//	private volatile static XutilsHttp instance;
//	public static XutilsHttp getInstance() {
//		if (instance == null) {
//			instance = new XutilsHttp();
//		}
//		return instance;
//
//
//	}


    public static XutilsHttp instance = new XutilsHttp();

    public static XutilsHttp getInstance() {
        return instance;
    }

    /**
     * 异步get请求
     *
     * @param url
     * @param maps
     * @param callBack
     */
    public void get(String url, Map<String, String> maps, final XCallBack callBack, final Context context) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
            params.setAsJsonContent(true);
        }
        utils = x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                callBack.onResponse(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });


    }


    /**
     * 异步post请求
     *
     * @param url
     * @param maps
     * @param callback
     */
    public void post(String url, Map<String, String> maps, final XCallBack callback, final Context context) {
        RequestParams params = new RequestParams(url);
        JSONObject jsonObject = new JSONObject();
        try {
            if (maps != null && !maps.isEmpty()) {
                maps.remove("Key");
                for (Map.Entry<String, String> entry : maps.entrySet()) {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
                params.setAsJsonContent(true);
                params.setBodyContent(jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        utils = x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                callback.onResponse(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onResponse(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    /**
     * 带缓存数据的异步 get请求
     *
     * @param url
     * @param maps
     * @param pnewCache
     * @param callback
     */
    public void getCache(String url, Map<String, String> maps, final boolean pnewCache, final XCallBack callback,
                         final Context context) {

        RequestParams params = new RequestParams(url);
        params.setCacheMaxAge(1000 * 60); //为请求添加缓存时间
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        utils = x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                callback.onResponse(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                boolean newCache = pnewCache;
                if (newCache) {
                    newCache = !newCache;
                }
                if (!newCache) {
                    newCache = !newCache;
                    callback.onResponse(result);
                }
                return newCache;
            }
        });
    }

    /**
     * 带缓存数据的异步 post请求
     *
     * @param url
     * @param maps
     * @param pnewCache
     * @param callback
     */
    public void postCache(String url, Map<String, String> maps, final boolean pnewCache, final XCallBack callback,
                          final Context context) {
        RequestParams params = new RequestParams(url);

        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        utils = x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.onResponse(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public boolean onCache(String result) {
                boolean newCache = pnewCache;
                if (newCache) {
                    newCache = !newCache;
                }
                if (!newCache) {
                    newCache = !newCache;
                    callback.onResponse(result);
                }
                return newCache;
            }
        });
    }

    /**
     * 正常图片显示
     *
     * @param iv
     * @param url
     * @param option
     * @param id     默认图Id
     */
    public void bindCommonImage(ImageView iv, String url, boolean option, int id) {
        if (option) {
            options = new ImageOptions.Builder().setFailureDrawableId(id).build();
            x.image().bind(iv, url, options);
        } else {
            x.image().bind(iv, url);
        }
    }

    /**
     * 圆形图片显示
     *
     * @param iv
     * @param url
     * @param option
     * @param id     默认图Id
     */
    public void bindCircularImage(ImageView iv, String url, boolean option, int id) {
        if (option) {
            options = new ImageOptions.Builder().setFailureDrawableId(id).setCircular(true).build();
            x.image().bind(iv, url, options);
        } else {
            x.image().bind(iv, url);
        }
    }

    /**
     * 文件上传
     *
     * @param url
     * @param maps
     * @param file
     * @param callback
     */
    public void upLoadFile(String url, Map<String, String> maps, Map<String, File> file, final XCallBack callback,
                           Context context) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (file != null) {
            for (Map.Entry<String, File> entry : file.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue().getAbsoluteFile());
            }
        }
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//				onSuccessResponse(result, callback);
                callback.onResponse(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    public interface XCallBack {
        void onResponse(String result);

        void onError(String result);
    }

    public void Cancle() {
        if (utils != null) {
            utils.cancel();
        }
    }

}
