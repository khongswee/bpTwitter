package com.bp.twitter.tweetsearch.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import com.bp.twitter.data.factory.SearchTweetResponseFactory
import com.bp.twitter.data.tweetsearch.repository.service.SearchRepositoryImpl
import com.bp.twitter.domain.GroupActionLoadList
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.domain.Status
import com.bp.twitter.domain.tweetsearch.SearchTweetUseCase
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class SearchTweetUseCaseTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private lateinit var useCase: SearchTweetUseCase
    @Mock
    private lateinit var repositoryImpl: SearchRepositoryImpl
    private val mapper = TweetDataMapToView()

    @Before
    fun setUp() {
        repositoryImpl = mock()
        useCase = SearchTweetUseCase(repositoryImpl)
        given(repositoryImpl.getPageSize()).willReturn(20)
    }

    @Test
    fun load_initial_success_and_empty_data() {
        val respone = SearchTweetResponseFactory.makeSearchTweetResponseEmpty()
        given(repositoryImpl.searchTwit("")).willReturn(Single.just(respone))
        val resultTweet = useCase.fetchTweet()
        val pagedList = getPagingList(resultTweet)
        assertThat(pagedList.size, `is`(0))
    }

    @Test
    fun load_initial_success_and_has_data() {
        val size = 3
        val respone = SearchTweetResponseFactory.makeSearchTweetResponseHasData(size)
        val map = TweetDataMapToView()
        val dataExpected = respone.statuses.map { map.mapToView(it) }
        given(repositoryImpl.searchTwit("")).willReturn(Single.just(respone))
        val resultTweet = useCase.fetchTweet()
        val pagedList = getPagingList(resultTweet)
        assertThat(pagedList.size, `is`(size))
        assertThat(pagedList,`is`(dataExpected))
    }

    @Test
    fun load_initial_error() {
        val exception = Throwable("FAILED")
        given(repositoryImpl.searchTwit("")).willReturn(Single.error(exception))
        val resultTweet = useCase.fetchTweet()
        val pagedList = getPagingList(resultTweet)
        val network = getInitailStateNetWork(resultTweet)
        assertThat(pagedList.size, `is`(0))
        assertThat(network.status, `is`(Status.FAILED))
        assertThat(network.message, equalTo("FAILED"))

    }

    @Test
    fun load_initial_and_after_success() {
        val size = 20
        val respone = SearchTweetResponseFactory.makeSearchTweetResponseHasData(size)
        val responeMore = SearchTweetResponseFactory.makeSearchTweetResponseHasData(size)
        val map = TweetDataMapToView()
        val dataExpected = respone.statuses.map { map.mapToView(it) }
        val dataExpectedMore = responeMore.statuses.map { map.mapToView(it) }

        given(repositoryImpl.searchTwit("")).willReturn(Single.just(respone))
        given(repositoryImpl.moreSearchTwit(respone.searchMetadata.maxIdStr)).willReturn(Single.just(responeMore))
        val resultTweet = useCase.fetchTweet()

        val pagedList = getPagingList(resultTweet)
        assertThat(pagedList.size, `is`(20))
        pagedList.loadAround(2)
        val network = getStateNetWork(resultTweet)
        assertThat(pagedList.size, `is`(40))
        assertThat(pagedList, `is`(dataExpected+dataExpectedMore))
        assertThat(network, `is`(NetworkState.LOADED))
    }

    @Test
    fun load_initial_success_and_after_error() {
        val size = 20
        val respone = SearchTweetResponseFactory.makeSearchTweetResponseHasData(size)
        val map = TweetDataMapToView()
        val dataExpected = respone.statuses.map { map.mapToView(it) }
        given(repositoryImpl.searchTwit("")).willReturn(Single.just(respone))

        val exception = Throwable("FAILED")
        given(repositoryImpl.moreSearchTwit(respone.searchMetadata.maxIdStr)).willReturn(Single.error(exception))

        val resultTweet = useCase.fetchTweet()
        val pagedList = getPagingList(resultTweet)
        assertThat(getInitailStateNetWork(resultTweet), `is`(NetworkState.LOADED))
        assertThat(pagedList.size, `is`(size))
        pagedList.loadAround(2)
        val network = getStateNetWork(resultTweet)
        assertThat(network.status, `is`(Status.FAILED))
        assertThat(network.message, equalTo("FAILED"))
        assertThat(pagedList.size, `is`(size))
        assertThat(pagedList, `is`(dataExpected))

    }

    @Test
    fun load_refresh_success() {
        val size = 20
        val respone = SearchTweetResponseFactory.makeSearchTweetResponseHasData(size)
        val map = TweetDataMapToView()
        val dataExpected = respone.statuses.map { map.mapToView(it) }

        given(repositoryImpl.searchTwit("")).willReturn(Single.just(respone))
        val resultTweet = useCase.fetchTweet()
        val pagedList = getPagingList(resultTweet)

        assertThat(getInitailStateNetWork(resultTweet), `is`(NetworkState.LOADED))
        assertThat(pagedList.size, `is`(size))
        assertThat(pagedList, `is`(dataExpected))

        val networkObserver = Mockito.mock(Observer::class.java) as Observer<NetworkState>
        resultTweet.initialState.observeForever(networkObserver)
        useCase.refresh()
        val network = getInitailStateNetWork(resultTweet)

        assertThat(pagedList.size, `is`(size))
        assertThat(pagedList, `is`(dataExpected))

        assertThat(network, `is`(NetworkState.LOADED))

    }

    @Test
    fun load_refresh_error() {
        val size = 20
        val respone = SearchTweetResponseFactory.makeSearchTweetResponseHasData(size)

        given(repositoryImpl.searchTwit("")).willReturn(Single.just(respone))
        val resultTweet = useCase.fetchTweet()
        val pagedList = getPagingList(resultTweet)


        assertThat(getInitailStateNetWork(resultTweet), `is`(NetworkState.LOADED))
        assertThat(pagedList.size, `is`(size))

        val networkObserver = Mockito.mock(Observer::class.java) as Observer<NetworkState>
        resultTweet.initialState.observeForever(networkObserver)
        val exception = Throwable("FAILED")
        given(repositoryImpl.searchTwit("")).willReturn(Single.error(exception))

        useCase.refresh()
        val network = getInitailStateNetWork(resultTweet)
        assertThat(network.status, `is`(Status.FAILED))
        assertThat(network.message, equalTo("FAILED"))
    }

    private fun getPagingList(result: GroupActionLoadList<TweetDisplayModel>): PagedList<TweetDisplayModel> {
        val observer = LogginObserver<PagedList<TweetDisplayModel>>()
        result.pagedList.observeForever(observer)
        MatcherAssert.assertThat(observer.value, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        return observer.value!!

    }



    private fun getStateNetWork(result: GroupActionLoadList<TweetDisplayModel>): NetworkState {
        val observer = LogginObserver<NetworkState>()
        result.networkState?.observeForever(observer)
        MatcherAssert.assertThat(observer.value, `is`(CoreMatchers.notNullValue()))
        return observer.value!!
    }

    private fun getInitailStateNetWork(result: GroupActionLoadList<TweetDisplayModel>): NetworkState {
        val observer = LogginObserver<NetworkState>()
        result.initialState?.observeForever(observer)
        MatcherAssert.assertThat(observer.value, `is`(CoreMatchers.notNullValue()))
        return observer.value!!
    }

    private class LogginObserver<T> : Observer<T> {
        var value: T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }
}