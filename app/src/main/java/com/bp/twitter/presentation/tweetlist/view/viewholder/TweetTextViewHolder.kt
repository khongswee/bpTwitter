package com.bp.twitter.presentation.tweetlist.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bp.twitter.R
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import kotlinx.android.synthetic.main.item_tweet.view.*

/**
 * Created by Administrator on 10/15/18.
 */
class TweetTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): TweetTextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_tweet, parent, false)
            return TweetTextViewHolder(view)
        }
    }

    fun bindToTweet(tweet: TweetDisplayModel) {
        itemView.tweetContentView.tweetContent = tweet
    }
}