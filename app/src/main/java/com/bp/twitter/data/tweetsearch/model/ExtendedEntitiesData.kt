package com.bp.twitter.data.tweetsearch.model

import com.google.gson.annotations.SerializedName


data class ExtendedEntitiesData(
    @SerializedName("media") val media: List<MediaData>
)

data class MediaData(
        @SerializedName("id") val id: Long,
        @SerializedName("id_str") val idStr: String,
        @SerializedName("indices") val indices: List<Int>,
        @SerializedName("media_url") val mediaUrl: String,
        @SerializedName("media_url_https") val mediaUrlHttps: String,
        @SerializedName("url") val url: String,
        @SerializedName("display_url") val displayUrl: String,
        @SerializedName("expanded_url") val expandedUrl: String,
        @SerializedName("type") val type: String,
        @SerializedName("sizes") val sizes: SizesData,
        @SerializedName("video_info") val videoInfo: VideoInfoData?
)

data class SizesData(
        @SerializedName("thumb") val thumb: SizeMediaData,
        @SerializedName("medium") val medium: SizeMediaData,
        @SerializedName("large") val large: SizeMediaData,
        @SerializedName("small") val small: SizeMediaData
)

data class SizeMediaData(
        @SerializedName("w") val w: Int,
        @SerializedName("h") val h: Int,
        @SerializedName("resize") val resize: String
)


data class VideoInfoData(
    @SerializedName("aspect_ratio") val aspectRatio: List<Int>,
    @SerializedName("duration_millis") val durationMillis: Int,
    @SerializedName("variants") val variants: List<VariantData>
)

data class VariantData(
    @SerializedName("bitrate") val bitrate: Int,
    @SerializedName("content_type") val contentType: String,
    @SerializedName("url") val url: String
)
