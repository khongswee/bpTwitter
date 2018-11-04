package com.bp.twitter.presentation.tweetlist.mapper

import android.util.Log
import com.bp.twitter.base.Mapper
import com.bp.twitter.data.tweetsearch.model.TweetData
import com.bp.twitter.presentation.tweetlist.model.HashTagDisplatModel
import com.bp.twitter.presentation.tweetlist.model.MediaTypeDisplay
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bp.twitter.presentation.tweetlist.model.TweetMediaDisplay
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class TweetDataMapToView : Mapper<TweetDisplayModel, TweetData> {
    override fun mapToView(data: TweetData): TweetDisplayModel {
        var isRetweet = false
        var nameRetweet = ""
        val tweet = data.retweetedStatus?.let {
            isRetweet = true
            nameRetweet = data.user.name
            return@let it
        } ?: run { data }
        val hashTags = identifyHashTag(tweet.text ?: "")
        var type = MediaTypeDisplay.TEXT
        var urls = listOf<String>()
        var videoThumbNail = ""
        tweet.extendedEntities?.let {
            if (it.media.isNotEmpty()) {
                when (it.media.first().type) {
                    "animated_gif" -> {
                        urls = it.media.first().videoInfo?.variants?.map { it.url } ?: emptyList()
                        type = MediaTypeDisplay.GIF
                        videoThumbNail = it.media.first().mediaUrl
                    }
                    "video" -> {
                        urls = it.media.first().videoInfo?.variants?.map { it.url } ?: emptyList()
                        type = MediaTypeDisplay.VIDEO
                        videoThumbNail = it.media.first().mediaUrl
                    }
                    else -> {
                        urls = it.media.map { it.mediaUrl }
                        type = MediaTypeDisplay.PHOTO
                    }
                }
            }
        }
        val tweetMedia = TweetMediaDisplay(type, urls, videoThumbNail)
        return TweetDisplayModel(data.id, tweet.user.name, tweet.user.screenName
                , tweet.user.profileImageUrl, isRetweet, nameRetweet, hashTags, tweet.text
                ?: "", convertTimeGMT(tweet.createdAt)
                , tweet.retweetCount, tweet.favoriteCount, tweetMedia)

//        return FakeVideo()
//        return FakeGif()
    }

    fun convertTimeGMT(input: String): String {
        return try {
            val dfNy = SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy")
            dfNy.timeZone = TimeZone.getTimeZone("UTC")
            val dfUtc = SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.getDefault())
            dfUtc.timeZone = TimeZone.getTimeZone("GMT+7")

            val date2 = dfNy.parse(input)

            val diff = Date().time - date2.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
//            Log.d("dsdsdsdsds", "sec:" + seconds + ",m:" + minutes + ",h:" + hours + ",d:" + days)

            if (minutes == 0L && hours == 0L && days == 0L) {
                return seconds.toString() + "s"
            } else if (hours == 0L && days == 0L) {
                return minutes.toString() + "m"
            } else if (days == 0L) {
                return hours.toString() + "h"
            } else if (days > 0L && days <= 7) {
                return days.toString() + "d"

            } else {
                return dfUtc.format(dfNy.parse(input))
            }


        } catch (e: Exception) {
            input
        }

    }

    private fun identifyHashTag(text: String): List<HashTagDisplatModel> {
        val matcher = Pattern.compile("#(?>\\p{L}\\p{M}*+|_)+").matcher(text)
        val listTag = arrayListOf<HashTagDisplatModel>()
        while (matcher.find()) {
            val hashTag = HashTagDisplatModel(text.substring(matcher.start(), matcher.end()), listOf(matcher.start(), matcher.end()))
            listTag.add(hashTag)
        }

        return listTag
    }

}