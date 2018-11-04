package com.bp.twitter.presentation.tweetgallery.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bp.twitter.R
import com.bumptech.glide.Glide
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.view_pager_photo.*
import android.graphics.Bitmap
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


class TweetImagePageFragment : Fragment() {

    private val KEY_IMAGE_URL = "media_url"

    companion object {
        fun newIntance(url: String): TweetImagePageFragment = TweetImagePageFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_IMAGE_URL, url)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_pager_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(KEY_IMAGE_URL)

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageView.setImage(ImageSource.bitmap(resource))

                    }

                })
    }
}