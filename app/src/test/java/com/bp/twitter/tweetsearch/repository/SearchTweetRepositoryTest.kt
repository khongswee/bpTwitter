package com.bp.twitter.tweetsearch.repository

import com.bp.twitter.data.factory.SearchTweetResponseFactory
import com.bp.twitter.data.tweetsearch.model.SearchTweetResponse
import com.bp.twitter.data.tweetsearch.repository.service.SearchRepositoryImpl
import com.bp.twitter.data.tweetsearch.repository.service.SearchTwitterApi
import com.bp.twitter.presentation.tweetlist.model.MediaTypeDisplay
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class SearchTweetRepositoryTest {
    private lateinit var repositoryImpl: SearchRepositoryImpl
    @Mock
    private lateinit var service: SearchTwitterApi

    private val keySearch = "#BLACKPINK"
    private val resultType = "recent"
    private val count = 20
    private val sinceId = ""
    private val maxId = ""


    @Before
    fun setUp() {
        service = mock()
        repositoryImpl = SearchRepositoryImpl(service)
    }

    @Test
    fun searchInitial() {
        val result = SearchTweetResponseFactory.makeSearchTweetResponseEmpty()
        val single = Single.just(result)
        given(service.fetchTweet(keySearch, resultType, repositoryImpl.getPageSize(), sinceId))
                .willReturn(single)

        val testObserver =  repositoryImpl.searchTwit().test()
        testObserver.assertValue(result)

        verify(service).fetchTweet(keySearch, resultType, repositoryImpl.getPageSize(), sinceId)
        verifyNoMoreInteractions(service)

    }

    @Test
    fun searchInitialHasData() {
        val result = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1,MediaTypeDisplay.TEXT)
        val single = Single.just(result)
        given(service.fetchTweet(keySearch, resultType, repositoryImpl.getPageSize(), sinceId))
                .willReturn(single)

        val testObserver =  repositoryImpl.searchTwit().test()
        testObserver.assertValue(result)

        verify(service).fetchTweet(keySearch, resultType, repositoryImpl.getPageSize(), sinceId)
        verifyNoMoreInteractions(service)

    }

    @Test
    fun searchMore() {
        val result = SearchTweetResponseFactory.makeSearchTweetResponseEmpty()
        val single = Single.just(result)
        given(service.fetchMoreTweet(keySearch, resultType, repositoryImpl.getPageSize(), maxId))
                .willReturn(single)

        val testObserver =  repositoryImpl.moreSearchTwit("").test()
        testObserver.assertValue(result)

        verify(service).fetchMoreTweet(keySearch, resultType, repositoryImpl.getPageSize(), maxId)
        verifyNoMoreInteractions(service)

    }

    @Test
    fun searchMoreHasData() {
        val result = SearchTweetResponseFactory.makeSearchTweetResponseEmpty()
        val single = Single.just(result)
        given(service.fetchMoreTweet(keySearch, resultType, repositoryImpl.getPageSize(), maxId))
                .willReturn(single)

        val testObserver =  repositoryImpl.moreSearchTwit("").test()
        testObserver.assertValue(result)

        verify(service).fetchMoreTweet(keySearch, resultType, repositoryImpl.getPageSize(), maxId)
        verifyNoMoreInteractions(service)

    }

    @Test
    fun searchInitialError() {
        val errorObject =Throwable("ABC")
        val single = Single.error<SearchTweetResponse>(errorObject)
        given(service.fetchTweet(keySearch, resultType, repositoryImpl.getPageSize(), sinceId))
                .willReturn(single)

        val testObserver = repositoryImpl.searchTwit().test()
        testObserver.assertError {
            it.localizedMessage == "ABC"
        }

        verify(service).fetchTweet(keySearch, resultType, count, sinceId)
        verifyNoMoreInteractions(service)

    }

    @Test
    fun searchMoreError() {
        val errorObject =Throwable("ABC")
        val single = Single.error<SearchTweetResponse>(errorObject)
        given(service.fetchMoreTweet(keySearch, resultType, repositoryImpl.getPageSize(), maxId))
                .willReturn(single)

        val testObserver = repositoryImpl.moreSearchTwit("").test()
        testObserver.assertError {
            it.localizedMessage == "ABC"
        }
        verify(service).fetchMoreTweet(keySearch, resultType, repositoryImpl.getPageSize(), maxId)
        verifyNoMoreInteractions(service)
    }

}