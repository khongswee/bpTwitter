package com.bp.twitter.data.tweetsearch.model
import com.google.gson.annotations.SerializedName



data class SearchMetaData(
    @SerializedName("completed_in") val completedIn: Double,
    @SerializedName("max_id") val maxId: Long,
    @SerializedName("max_id_str") val maxIdStr: String,
    @SerializedName("query") val query: String,
    @SerializedName("refresh_url") val refreshUrl: String,
    @SerializedName("count") val count: Int,
    @SerializedName("since_id") val sinceId: Long,
    @SerializedName("since_id_str") val sinceIdStr: String
)