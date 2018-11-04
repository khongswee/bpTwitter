package com.bp.twitter.data.tweetsearch.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.bp.twitter.data.tweetsearch.model.TweetData
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.domain.tweetsearch.SearchListRepository
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import io.reactivex.Scheduler
import javax.inject.Inject

class SearchTweetItemDataSource @Inject constructor(val repository: SearchListRepository, val mapper: TweetDataMapToView, val scheduler: Scheduler) : ItemKeyedDataSource<Long, TweetData>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<TweetData>) {
        initialState.postValue(NetworkState.LOADING)
        repository.searchTwit("").subscribe({
            initialState.postValue(NetworkState.LOADED)
            callback.onResult(it.statuses)
        }, {
            initialState.postValue(NetworkState.error(it.localizedMessage))
        })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<TweetData>) {
        networkState.postValue(NetworkState.LOADING)
        repository.moreSearchTwit(params.key.toString()).subscribe({
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(it.statuses)

        }, {
            networkState.postValue(NetworkState.error(it.localizedMessage))
        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<TweetData>) {
    }

    override fun getKey(item: TweetData): Long = item.id

}