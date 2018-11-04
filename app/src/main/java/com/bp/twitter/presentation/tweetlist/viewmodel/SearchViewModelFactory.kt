package com.bp.twitter.presentation.tweetlist.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bp.twitter.domain.tweetsearch.SearchTweetUseCase
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(val useCase: SearchTweetUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchListViewModel::class.java!!)) {
            return SearchListViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}