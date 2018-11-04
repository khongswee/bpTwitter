package com.bp.twitter.data.tweetsearch.di

import com.bp.twitter.domain.tweetsearch.SearchTweetUseCase
import com.bp.twitter.domain.tweetsearch.SearchListRepository
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

@Module
class SearchUseCaseModule {
    @Provides
    fun provideSearchUseCase( repositoryImpl2: SearchListRepository): SearchTweetUseCase = SearchTweetUseCase(repositoryImpl2)

}