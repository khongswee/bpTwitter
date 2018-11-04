package com.bp.twitter.presentation.tweetlist.view.viewholder

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bp.twitter.R
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import im.ene.toro.ToroPlayer
import im.ene.toro.ToroUtil
import im.ene.toro.media.PlaybackInfo
import im.ene.toro.widget.Container
import kotlinx.android.synthetic.main.item_gif.view.*

/**
 * Created by Administrator on 10/15/18.
 */
class TweetGifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ToroPlayer {
    private var mediaUri: Uri? = null
    private var helper: LoopingPlayerHelper? = null


    private val toroPlayerEventListener = object : ToroPlayer.EventListener {
        override fun onBuffering() {
            itemView.thumniailVideoView.visibility = View.GONE
        }

        override fun onPlaying() {
            itemView.thumniailVideoView.visibility = View.GONE
        }

        override fun onPaused() {
        }

        override fun onCompleted() {
            // do something
        }
    }


    override fun isPlaying(): Boolean = helper != null && helper?.isPlaying ?: false

    override fun getPlayerView(): View = itemView.videoPlayer

    override fun wantsToPlay(): Boolean {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.75
    }

    override fun play() {
        if (helper != null) helper!!.play()
    }

    override fun pause() {
        if (helper != null) helper!!.pause()
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo {
        return helper?.let {
            it.latestPlaybackInfo
        } ?: run { PlaybackInfo() }
    }


    override fun release() {
        if (helper != null) {
            helper!!.removePlayerEventListener(toroPlayerEventListener)
            helper!!.release()
            helper = null
        }
    }

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
        if (mediaUri == null) throw IllegalStateException("mediaUri is null.")
        if (helper == null) {
            helper = LoopingPlayerHelper(this, mediaUri!!)
            helper!!.addPlayerEventListener(toroPlayerEventListener)
        }
        helper!!.initialize(container, playbackInfo)
    }

    override fun getPlayerOrder(): Int = adapterPosition

    companion object {
        fun create(parent: ViewGroup): TweetGifViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_gif, parent, false)
            return TweetGifViewHolder(view)
        }
    }


    fun bindToGift(tweet: TweetDisplayModel) {
        itemView.thumniailVideoView.visibility = View.VISIBLE
        Glide.with(itemView)
                .load(tweet.media.thumbnaiVideo)
                .apply(RequestOptions().fitCenter())
                .thumbnail(0.15f)
                .into(itemView.thumniailVideoView)
        mediaUri = Uri.parse(tweet.media.urls.first())
        itemView.tweetContentView.tweetContent = tweet
    }

}