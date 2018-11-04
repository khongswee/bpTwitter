package com.bp.twitter.data.factory

import com.bp.twitter.data.tweetsearch.model.SearchMetaData
import com.bp.twitter.data.tweetsearch.model.SearchTweetResponse
import com.bp.twitter.data.tweetsearch.model.TweetData
import com.bp.twitter.presentation.tweetlist.model.MediaTypeDisplay
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream


class SearchTweetResponseFactory {
    companion object {
        fun makeSearchTweetResponseEmpty(): SearchTweetResponse {
            val list = listOf<TweetData>()
            return SearchTweetResponse(list, SearchMetaData(0.0, 0, ""
                    , "", "", 0, 0, ""))
        }

        fun makeSearchTweetResponseHasData(size: Int, type: MediaTypeDisplay=MediaTypeDisplay.TEXT, isRetweet: Boolean = false): SearchTweetResponse {
            val list = arrayListOf<TweetData>()
            repeat(size) {
                list.add(makeTweetText(type, isRetweet))
            }
            return SearchTweetResponse(list, createSearchMetaData(list))
        }

        private fun makeTweetText(type: MediaTypeDisplay,isRetweet: Boolean): TweetData {
            val source = javaClass.getResourceAsStream(selectPathFileJsonData(type, isRetweet))
            val bis = BufferedInputStream(source)
            val buf = ByteArrayOutputStream()
            var result = bis.read()
            while (result != -1) {
                buf.write(result.toByte().toInt())
                result = bis.read()
            }
            val jsonText = buf.toString("UTF-8")
            return Gson().fromJson(jsonText, TweetData::class.java)
        }

        private fun selectPathFileJsonData(type: MediaTypeDisplay, isRetweet: Boolean): String {
            return when (type) {
                MediaTypeDisplay.GIF -> {
                    return if (!isRetweet) "/tweet_gif.json" else "/tweet_gif_retweet.json"
                }
                MediaTypeDisplay.PHOTO -> {
                    return if (!isRetweet) "/tweet_photo.json" else "/tweet_photo_retweet.json"
                }
                MediaTypeDisplay.VIDEO -> {
                    return if (!isRetweet) "/tweet_video.json" else "/tweet_video_retweet.json"
                }
                MediaTypeDisplay.TEXT -> {
                    return if (!isRetweet) "/tweet_text.json" else "/tweet_text_retweet.json"
                }
            }
        }

        private fun createSearchMetaData(list: List<TweetData>): SearchMetaData {
            return SearchMetaData(0.0, list.last().id, list.last().idStr
                    , "0", "", list.size, list.first().id, list.first().idStr)
        }


    }


}