package com.bp.twitter.data.tweetsearch.di

import com.bp.twitter.domain.tweetsearch.SearchListRepository
import com.bp.twitter.data.tweetsearch.repository.service.SearchTwitterApi
import com.bp.twitter.data.tweetsearch.repository.service.SearchRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class SearchRepositoryModule {
    @Provides
    fun provideSearchRepository(service: SearchTwitterApi): SearchListRepository = SearchRepositoryImpl(service)
}