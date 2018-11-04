package com.bp.twitter.data.tweetsearch.repository.service

import com.bp.twitter.data.tweetsearch.model.SearchTweetResponse
import com.bp.twitter.domain.tweetsearch.SearchListRepository
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(val service: SearchTwitterApi) : SearchListRepository {
    override fun getPageSize(): Int =20

    override fun moreSearchTwit(max_id: String): Single<SearchTweetResponse> {
        return service.fetchMoreTweet("#BLACKPINK", "recent", getPageSize(), max_id)
    }

    override fun searchTwit(since_id: String): Single<SearchTweetResponse> {
       return service.fetchTweet("#BLACKPINK", "recent", getPageSize(), since_id)
    }

}