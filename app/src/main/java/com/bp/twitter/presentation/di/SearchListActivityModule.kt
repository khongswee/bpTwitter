package com.bp.twitter.presentation.di

import com.bp.twitter.domain.tweetsearch.SearchTweetUseCase
import com.bp.twitter.presentation.tweetlist.viewmodel.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SearchListActivityModule {

    @Provides
    fun provideSearchViewModelFactory(useCase: SearchTweetUseCase): SearchViewModelFactory {
        return SearchViewModelFactory(useCase)
    }
}