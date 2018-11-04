package com.bp.twitter.presentation.tweetlist.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bp.twitter.R
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.presentation.tweetlist.model.MediaTypeDisplay
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bp.twitter.presentation.tweetlist.view.viewholder.*


class SearchTweetAdapter : PagedListAdapter<TweetDisplayModel, RecyclerView.ViewHolder>(DiffCallback) {
    private var networkState: NetworkState? = null

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<TweetDisplayModel>() {
            override fun areItemsTheSame(oldItem: TweetDisplayModel, newItem: TweetDisplayModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TweetDisplayModel, newItem: TweetDisplayModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            R.layout.item_video -> TweetVideoViewHolder.create(parent)
            R.layout.item_gif -> TweetGifViewHolder.create(parent)
            R.layout.item_photo -> TweetPhotoViewHolder.create(parent)
            R.layout.item_tweet -> TweetTextViewHolder.create(parent)
            else -> NetworkStateViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tweet = getItem(position)
        tweet?.let {
            when (getItemViewType(position)) {
                R.layout.item_video -> {
                    val holderItem = (holder as TweetVideoViewHolder)
                    holderItem.bindToVideo(it)
                }
                R.layout.item_gif -> {
                    val holderItem = (holder as TweetGifViewHolder)
                    holderItem.bindToGift(it)
                }
                R.layout.item_photo -> {
                    (holder as TweetPhotoViewHolder).bindToListPhoto(it, listTweetPhoto)
                }
                R.layout.item_tweet -> {
                    (holder as TweetTextViewHolder).bindToTweet(it)
                }
                else -> {
                    (holder as NetworkStateViewHolder).bindTo(networkState)
                }
            }
        }


    }


    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1) return R.layout.item_network_state

        getItem(position)?.let {
            return when (it.media.type) {
                MediaTypeDisplay.GIF -> R.layout.item_gif
                MediaTypeDisplay.PHOTO -> R.layout.item_photo
                MediaTypeDisplay.VIDEO -> R.layout.item_video
                else -> R.layout.item_tweet
            }
        } ?: run { return R.layout.item_tweet }

    }

    var listTweetPhoto: OnTweetPhotoClick? = null

    interface OnTweetPhotoClick {
        fun onClickPhoto(list: List<String>, position: Int)
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

}