package com.bp.twitter.presentation.tweetlist.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bp.twitter.R
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.domain.Status
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindTo(item: NetworkState?) {
        itemView.pbLoading.visibility = when (item?.status) {
            Status.RUNNING -> View.VISIBLE
            else -> View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
            return NetworkStateViewHolder(view)
        }
    }
}