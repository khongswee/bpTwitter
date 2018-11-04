package com.bp.twitter.data.tweetsearch.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.bp.twitter.data.tweetsearch.model.TweetData
import com.bp.twitter.domain.tweetsearch.SearchListRepository
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import io.reactivex.Scheduler


class SearchTweeDatasourceFactory(val repository: SearchListRepository, val mapper: TweetDataMapToView, val scheduler: Scheduler) : DataSource.Factory<Long, TweetData>() {
    val dataSourceLiveData = MutableLiveData<SearchTweetItemDataSource>()
    override fun create(): DataSource<Long, TweetData> {
        val dataSource = SearchTweetItemDataSource(repository, mapper, scheduler)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}