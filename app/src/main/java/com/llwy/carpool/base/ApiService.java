package com.llwy.carpool.base;


import com.llwy.carpool.model.News;
import com.llwy.carpool.model.UserBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 *
 */
public interface ApiService {
    //baseUrl
    String API_SERVER_URL = "http://newapi.bopinwang.com/api/";
    //NewUrl
    String API_SERVER_URL_NEW = "http://www.zangweijia.top:8080/ZWJ_First/ZWJ/";


    @GET("P/TopCategory")
    Observable<ResultResponse<List<News>>> getNews();

    @GET
    Observable<ResultResponse<UserBean>> Login(@Url String url,@QueryMap Map<String, String> userBean);


}
