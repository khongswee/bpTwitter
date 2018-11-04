package com.bp.twitter.data.tweetsearch.repository.service

import com.bp.twitter.data.tweetsearch.model.SearchTweetResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchTwitterApi {
    @GET("1.1/search/tweets.json")
    fun fetchTweet(@Query("q")q:String,
                   @Query("result_type")result_type:String,
                   @Query("count")count:Int,
                   @Query("since_id")since_id:String,
                   @Query("tweet_mode")mode:String="extended"): Single<SearchTweetResponse>

    @GET("1.1/search/tweets.json")
    fun fetchMoreTweet(@Query("q")q:String,
                   @Query("result_type")result_type:String,
                   @Query("count")count:Int,
                   @Query("max_id")max_id:String,
                   @Query("tweet_mode")mode:String="extended"): Single<SearchTweetResponse>
}