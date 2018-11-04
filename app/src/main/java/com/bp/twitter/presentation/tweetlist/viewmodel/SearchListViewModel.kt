package com.bp.twitter.presentation.tweetlist.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.domain.tweetsearch.SearchTweetUseCase
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import javax.inject.Inject

class SearchListViewModel @Inject constructor(val useCase: SearchTweetUseCase) : ViewModel() {

    lateinit var networkState: LiveData<NetworkState>
    lateinit var initialState: LiveData<NetworkState>
    lateinit var tweetList: LiveData<PagedList<TweetDisplayModel>>

    init {
        useCase.fetchTweet().let {
            tweetList = it.pagedList
            networkState = it.networkState
            initialState = it.initialState
        }
    }

    fun reload() {
        useCase.refresh()
    }

}