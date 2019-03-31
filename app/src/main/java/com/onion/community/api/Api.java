package com.onion.community.api;


import com.onion.community.bean.*;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

/**
 */
public interface Api {

//    String BASE_URL = "http://118.25.14.84:8050/";
    String BASE_URL = "http://192.168.124.14:8050/";


    @FormUrlEncoded
    @POST("user/login")
    Flowable<HttpWrapper<User>> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Flowable<HttpWrapper<User>> register(@Field("phone") String phone, @Field("password") String password);

    @POST("user/getBanner")
    Flowable<HttpWrapper<List<Banners>>> getBanner();

    @FormUrlEncoded
    @POST("user/getFriend")
    Flowable<HttpWrapper<User>> getFriend(@Field("userId") String id);

    @POST("community/getNews")
    Flowable<HttpWrapper<List<Article>>> getNews();

    @POST("community/getCommunityType")
    Flowable<HttpWrapper<List<CommunityType>>> getCommunityType();

    @FormUrlEncoded
    @POST("community/getCommunityForId")
    Flowable<HttpWrapper<List<Community>>> getCommunityForId(@Field("communityTypeId") String communityTypeId);

    @FormUrlEncoded
    @POST("community/getMyFollowCommunity")
    Flowable<HttpWrapper<List<Community>>> getFollowCommunity(@Field("userId")String userId);


    /**
     * 关注论坛
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("community/followCommunity")
    Flowable<HttpWrapper<Community>> followCommunity(@Field("userId")String userId,@Field("communityId") String communityId);

    /**
     * 得到论坛信息
     * @return
     */
    @FormUrlEncoded
    @POST("community/getCommunityInfo")
    Flowable<HttpWrapper<Community>> getCommunityInfo(@Field("communityId") String communityId);

    @FormUrlEncoded
    @POST("article/getCommunityArticles")
    Flowable<HttpWrapper<List<Article>>> getCommunityArticle(@Field("type") int type,@Field("communityId") String communityId,
                                                       @Field("page") int page,

                                                       @Field("pageSize") int pageSize);

    /**
     * 发帖时候 插入 上传图片
     *
     * @param uuid
     * @param map
     * @return
     */
    @Multipart
    @POST("image/uploadImage")
    Flowable<HttpWrapper<String>> uploadImage(@Part("uuid") String uuid, @PartMap Map<String, RequestBody> map);

    /**
     * 发帖
     * @param article
     * @return
     */
    @POST("article/saveCommunityArticles")
    Flowable<HttpWrapper<String>> post(@Body Article article);

    /**
     * 收藏帖子
     * @param userId
     * @param articleId
     * @return
     */
    @FormUrlEncoded
    @POST("article/collection")
    Flowable<HttpWrapper<String>> collection(@Field("userId") String userId,@Field("articleId") String articleId);

    /**
     * 得到一个帖子详情
     * @param articleId
     * @return
     */
    @FormUrlEncoded
    @POST("article/getArticleByArticleId")
    Flowable<HttpWrapper<Article>> getArticle(@Field("articleId") String articleId);

    /**
     * 得到我的收藏
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("article/getMyCollection")
    Flowable<HttpWrapper<List<Article>>> getMyCollection(@Field("userId") String userId);

    /**
     * 得到我的活动
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("article/getMyActivity")
    Flowable<HttpWrapper<List<Article>>> getMyActivity(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("community/getMyCommunity")
    Flowable<HttpWrapper<List<Community>>> getMyCommunity(@Field("userId") String userId);

    /**
     * 回复
     * @param data
     * @param id
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("article/huifu")
    Flowable<HttpWrapper<String>> huifu(@Field("content") String data,@Field("articleId") String id,@Field("userId") String userId);

    @FormUrlEncoded
    @POST("article/baoming")
    Flowable<HttpWrapper<String>> baoming(@Field("userId") String id,@Field("articleId") String id1);
}

