package com.bp.twitter.data.tweetsearch.model
import com.google.gson.annotations.SerializedName



data class TweetData(
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("id") val id: Long,
        @SerializedName("id_str") val idStr: String,
        @SerializedName("full_text") val text: String?,
        @SerializedName("user") val user: UserData,
        @SerializedName("truncated") val truncated: Boolean,
        @SerializedName("entities") val entities: EntitiesData,
        @SerializedName("extended_entities") val extendedEntities: ExtendedEntitiesData?,
        @SerializedName("retweeted_status") val retweetedStatus: TweetData?,
        @SerializedName("is_quote_status") val isQuoteStatus: Boolean,
        @SerializedName("retweet_count") val retweetCount: Int,
        @SerializedName("favorite_count") val favoriteCount: Int,
        @SerializedName("favorited") val favorited: Boolean,
        @SerializedName("retweeted") val retweeted: Boolean,
        @SerializedName("possibly_sensitive") val possiblySensitive: Boolean,
        @SerializedName("lang") val lang: String

)