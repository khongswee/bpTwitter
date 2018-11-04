package com.bp.twitter.presentation.tweetlist.view.viewholder

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bp.twitter.R
import com.bp.twitter.presentation.tweetlist.view.adapter.SearchTweetAdapter
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bp.twitter.presentation.tweetlist.view.adapter.TweetPhotoMediaAdapter
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * Created by Administrator on 10/15/18.
 */
class TweetPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): TweetPhotoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_photo, parent, false)
            return TweetPhotoViewHolder(view)
        }
    }

    fun bindToListPhoto(tweet: TweetDisplayModel, listTweetPhoto: SearchTweetAdapter.OnTweetPhotoClick?){
        val layoutManager = GridLayoutManager(itemView.context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (tweet.media.urls.size) {
                    1 -> 2
                    else -> 1
                }
            }
        }
        val adp = TweetPhotoMediaAdapter()
        itemView.rvMedia.layoutManager = layoutManager
        itemView.rvMedia.adapter = adp
        adp.listenerClick = object : TweetPhotoMediaAdapter.OnClickItem {
            override fun onClickItem(list: List<String>, position: Int) {
                listTweetPhoto?.onClickPhoto(list, position)
            }

        }
        adp.setMeiaList(tweet.media.urls)

        itemView.tweetContentView.tweetContent = tweet
    }
}