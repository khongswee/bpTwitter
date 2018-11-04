package com.bp.twitter.domain.tweetsearch

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.bp.twitter.data.tweetsearch.datasource.SearchTweeDatasourceFactory
import com.bp.twitter.data.tweetsearch.datasource.SearchTweetItemDataSource
import com.bp.twitter.domain.GroupActionLoadList
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchTweetUseCase @Inject constructor(val repository: SearchListRepository) {
    val mapper = TweetDataMapToView()
    val scheduler = Schedulers.io()
    val factoryItemKey = SearchTweeDatasourceFactory(repository, mapper, scheduler)

    fun fetchTweet(): GroupActionLoadList<TweetDisplayModel> {
        val config = PagedList.Config.Builder()
                .setPageSize(repository.getPageSize())
                .setInitialLoadSizeHint(repository.getPageSize() * 2)
                .setEnablePlaceholders(false)
                .build()
        val result = LivePagedListBuilder(factoryItemKey.map { mapper.mapToView(it) }, config).build()
        val initialState = Transformations.switchMap<SearchTweetItemDataSource, NetworkState>(
                factoryItemKey.dataSourceLiveData) { it.initialState }
        val networkState = Transformations.switchMap<SearchTweetItemDataSource, NetworkState>(
                factoryItemKey.dataSourceLiveData) { it.networkState }

        return GroupActionLoadList(result, networkState, initialState)
    }

    fun refresh() {
        factoryItemKey.dataSourceLiveData.value?.invalidate()
    }

}