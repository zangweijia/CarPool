package com.llwy.llwystage.base;


import com.llwy.llwystage.model.News;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 *
 */
public interface ApiService {
    //baseUrl
    String API_SERVER_URL = "http://newapi.bopinwang.com/api/";


    /**
     * //     * 获取新闻数据列表
     * //
     */
    @GET("P/TopCategory")
    Observable<ResultResponse<List<News>>> getNews();


//    /**
//     * 获取新闻数据列表
//     */
//    @GET(URL_ARTICLE_FEED + "?source=2&as=A1C528E25E76FB8&cp=582EC64FEBD84E1")
//    Observable<ResultResponse<List<News>>> getNews(@Query("category") String category);

//    /**
//     * 获取评论数据
//     *
//     * @param group_id
//     * @param item_id
//     * @param offset
//     * @param count
//     * @return
//     */
//    @GET(URL_COMMENT_LIST)
//    Observable<ResultResponse<CommentList>> getComment(@Query("group_id") String group_id, @Query("item_id") String item_id, @Query("offset") String offset, @Query("count") String count);

//    /**
//     * 获取新闻详情
//     */
//    @GET
//    Observable<ResultResponse<NewsDetail>> getNewsDetail(@Url String url);

}
