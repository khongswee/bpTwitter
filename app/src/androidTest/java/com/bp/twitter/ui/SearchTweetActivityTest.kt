package com.bp.twitter.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.bp.twitter.R
import com.bp.twitter.TestApplication
import com.bp.twitter.data.factory.SearchTweetResponseFactory
import com.bp.twitter.data.tweetsearch.model.SearchTweetResponse
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import com.bp.twitter.presentation.tweetlist.model.MediaTypeDisplay
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bp.twitter.presentation.tweetlist.view.SearchListActivity
import com.bp.twitter.util.EspressoTestsMatchers.*
import io.reactivex.Single
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given


@RunWith(AndroidJUnit4::class)
class SearchTweetActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<SearchListActivity>(SearchListActivity::class.java, false, false)

    @Test
    fun tweetContentTextDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.TEXT)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsNotRetweetContent(index)
        }
    }

    @Test
    fun tweetRetweetAndContentTextDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.TEXT, true)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsRetweetContent(tweetDisplayModel, index)
        }

    }

    @Test
    fun tweetContentGifDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.GIF)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsNotRetweetContent(index)
            onView(withRecyclerView(R.id.rvTweet).atPosition(index))
                    .check(matches(hasDescendant(withText("GIF"))))
        }
    }


    @Test
    fun tweetRetweetAndContentGifDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.GIF, true)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsRetweetContent(tweetDisplayModel, index)
            onView(withRecyclerView(R.id.rvTweet).atPosition(index))
                    .check(matches(hasDescendant(withText("GIF"))))
        }
    }

    @Test
    fun tweetContentPhotoDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.PHOTO)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsNotRetweetContent(index)
            onView(withRecyclerView(R.id.rvTweet).atPositionOnView(index, R.id.rvMedia))
                    .check(matchItemCount(tweetDisplayModel.media.urls.size))
        }
    }


    @Test
    fun tweetRetweetAndContentPhotoDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.PHOTO, true)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsRetweetContent(tweetDisplayModel, index)
            onView(withRecyclerView(R.id.rvTweet).atPositionOnView(index, R.id.rvMedia))
                    .check(matchItemCount(tweetDisplayModel.media.urls.size))
        }
    }

    @Test
    fun tweetContentVideoDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.VIDEO)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsNotRetweetContent(index)
            onView(withRecyclerView(R.id.rvTweet).atPositionOnView(index, R.id.videoPlayer))
                    .check(matches((isDisplayed())))
        }
    }

    @Test
    fun tweetRetweetAndContentVideoDisplay() {
        val tweet = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, MediaTypeDisplay.VIDEO, true)
        val mapperTweet = TweetDataMapToView()
        val tweetDisplay = tweet.statuses.map { mapperTweet.mapToView(it) }
        stubRepositoryGetSearchTweet(tweet)
        activity.launchActivity(null)

        tweetDisplay.forEachIndexed { index, tweetDisplayModel ->
            checkTweetTextContentDisplay(tweetDisplayModel, index)
            checkIsRetweetContent(tweetDisplayModel, index)
            onView(withRecyclerView(R.id.rvTweet).atPositionOnView(index, R.id.videoPlayer))
                    .check(matches((isDisplayed())))
        }
    }


    private fun stubRepositoryGetSearchTweet(tweet: SearchTweetResponse) {
        val single = Single.just(tweet)
        given(TestApplication.appComponent().searchRepository2().getPageSize()).willReturn(10)
        given(TestApplication.appComponent().searchRepository2().searchTwit()).willReturn(single)
        given(TestApplication.appComponent().searchRepository2().moreSearchTwit(tweet.searchMetadata.maxId.toString())).willReturn(single)
    }


    private fun checkTweetTextContentDisplay(tweet: TweetDisplayModel, position: Int) {
        onView(withRecyclerView(R.id.rvTweet).atPosition(position))
                .check(matches(hasDescendant(withText(tweet.namePost))))
        onView(withRecyclerView(R.id.rvTweet).atPosition(position))
                .check(matches(hasDescendant(withText(tweet.screenNamePost))))
        onView(withRecyclerView(R.id.rvTweet).atPosition(position))
                .check(matches(hasDescendant(withText(tweet.text))))
        onView(withRecyclerView(R.id.rvTweet).atPosition(position))
                .check(matches(hasDescendant(withText(tweet.timePost))))

    }

    private fun checkIsRetweetContent(tweet: TweetDisplayModel, position: Int) {
        onView(withRecyclerView(R.id.rvTweet).atPositionOnView(position, R.id.iconRetweet))
                .check(matches((isDisplayed())))
        onView(withRecyclerView(R.id.rvTweet).atPositionOnView(position, R.id.iconRetweet))
                .check(matches(withDrawable(R.drawable.ic_swap_horiz_black_24dp)))
        onView(withRecyclerView(R.id.rvTweet).atPositionOnView(position, R.id.tvAccountRetweet))
                .check(matches((isDisplayed())))
        onView(withRecyclerView(R.id.rvTweet).atPosition(position))
                .check(matches(hasDescendant(withText(tweet.nameRetweet))))
    }

    private fun checkIsNotRetweetContent(position: Int) {
        onView(withRecyclerView(R.id.rvTweet).atPositionOnView(position, R.id.iconRetweet))
                .check(matches(not(isDisplayed())))
        onView(withRecyclerView(R.id.rvTweet).atPositionOnView(position, R.id.tvAccountRetweet))
                .check(matches(not(isDisplayed())))
    }

}