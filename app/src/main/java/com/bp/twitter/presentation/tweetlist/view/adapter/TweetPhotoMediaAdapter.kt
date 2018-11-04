package com.bp.twitter.presentation.tweetlist.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bp.twitter.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_page_photo.view.*

class TweetPhotoMediaAdapter : RecyclerView.Adapter<TweetPhotoMediaAdapter.TweetMediaViewHolder>() {
    var mediaList = listOf<String>()
    fun setMeiaList(list: List<String>) {
        mediaList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetMediaViewHolder {
        return TweetMediaViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_page_photo, parent, false))
    }

    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: TweetMediaViewHolder, position: Int) {
            Glide.with(holder.itemView.context).load(mediaList[position])
                    .into(holder.itemView.imageMedie)

            holder.itemView.setOnClickListener {
                listenerClick?.onClickItem(mediaList,position)
            }

    }

      var listenerClick : OnClickItem?=null

interface OnClickItem{
    fun onClickItem(list:List<String>,position:Int)
}
    class TweetMediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}