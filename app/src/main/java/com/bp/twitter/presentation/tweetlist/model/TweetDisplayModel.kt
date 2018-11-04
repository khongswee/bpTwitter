package com.bp.twitter.presentation.tweetlist.model

data class TweetDisplayModel(val id: Long, val namePost: String, val screenNamePost: String,
                             val profileImage: String, val isReTweet: Boolean, val nameRetweet: String = "",
                             val hashTags: List<HashTagDisplatModel>, val text: String, val timePost: String,
                             val retweetCount: Int, val favoriteCount: Int, val media: TweetMediaDisplay
)

data class HashTagDisplatModel(val text:String,val indices:List<Int>)

data class TweetMediaDisplay(val type: MediaTypeDisplay, val urls: List<String>, val thumbnaiVideo:String)

enum class MediaTypeDisplay {
    TEXT, GIF, VIDEO, PHOTO
}