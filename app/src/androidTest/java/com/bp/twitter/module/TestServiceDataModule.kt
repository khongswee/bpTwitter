package com.bp.twitter.module

import com.bp.twitter.data.tweetsearch.repository.service.SearchTwitterApi
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides

@Module
class TestServiceDataModule {

    @Provides
    fun provideTwitterServiceApi(): SearchTwitterApi = mock()

}