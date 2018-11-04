package com.bp.twitter.data.tweetsearch.model

import com.google.gson.annotations.SerializedName

data class SymbolsData(
        @SerializedName("indices") val indices: List<Int>,
        @SerializedName("text") val text: String?
)

data class HashTagData(
        @SerializedName("text") val text: String?,
        @SerializedName("indices") val indices: List<Int>
)

data class UrlData(
        @SerializedName("indices") val indices: List<Int>,
        @SerializedName("url") val url: String,
        @SerializedName("display_url") val displayUrl: String,
        @SerializedName("expanded_url") val expandedUrl: String
)

data class UserMentionData(
        @SerializedName("name") val name: String,
        @SerializedName("indices") val indices: List<Int>,
        @SerializedName("screen_name") val screenName: String,
        @SerializedName("id") val id: Long,
        @SerializedName("id_str") val idStr: String
)

data class PollsData(
        @SerializedName("options") val options: List<OptionData>,
        @SerializedName("end_datetime") val endDatetime: String,
        @SerializedName("duration_minutes") val durationMinutes: Int
)

data class OptionData(
    @SerializedName("position") val position: Int,
    @SerializedName("text") val text: String
)


data class EntitiesData(
        @SerializedName("hashtags") val hashtags: List<HashTagData>,
        @SerializedName("urls") val urls: List<UrlData>,
        @SerializedName("user_mentions") val userMentions: List<UserMentionData>,
        @SerializedName("symbols") val symbols: List<SymbolsData>,
        @SerializedName("polls") val polls:List<PollsData>
)