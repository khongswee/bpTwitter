package com.bp.twitter.domain.tweetsearch

import com.bp.twitter.data.tweetsearch.model.SearchTweetResponse
import io.reactivex.Single

interface SearchListRepository {
    fun searchTwit(since_id:String=""): Single<SearchTweetResponse>
    fun moreSearchTwit(max_id:String): Single<SearchTweetResponse>
    fun getPageSize():Int

}