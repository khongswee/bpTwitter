package com.bp.twitter.presentation.tweetlist.view

import android.arch.paging.PagedList
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel

sealed class StateSearchTweet {

    data class StateTwittLoaded(val tweet: PagedList<TweetDisplayModel>) : StateSearchTweet()
    data class StateNetwork(val networkState: NetworkState) : StateSearchTweet()
    data class StateInitial(val networkState: NetworkState) : StateSearchTweet()

}