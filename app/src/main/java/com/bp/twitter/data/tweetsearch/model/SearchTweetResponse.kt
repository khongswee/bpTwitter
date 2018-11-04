package com.bp.twitter.data.tweetsearch.model

import com.google.gson.annotations.SerializedName

data class SearchTweetResponse(@SerializedName("statuses") val statuses: List<TweetData>,
                               @SerializedName("search_metadata") val searchMetadata: SearchMetaData)
