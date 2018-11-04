package com.bp.twitter.data.tweetsearch.di

import com.bp.twitter.domain.tweetsearch.SearchListRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestSearchRepositoryModule {
    @Singleton
    @Provides
    fun provideSearchRepository(): SearchListRepository = mock()
}