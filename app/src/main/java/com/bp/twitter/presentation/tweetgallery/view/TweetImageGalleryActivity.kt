package com.bp.twitter.presentation.tweetgallery.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bp.twitter.R
import com.bp.twitter.presentation.tweetgallery.adapter.TweetImageViewpagerAdapter
import kotlinx.android.synthetic.main.activity_tweet_gallery.*

class TweetImageGalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet_gallery)
        val list = intent.getStringArrayListExtra("list")
        val position = intent.getIntExtra("position",0)
        val viewpagerAdapter = TweetImageViewpagerAdapter(supportFragmentManager, list)
        viewpager.adapter = viewpagerAdapter
        viewpager.currentItem = position
    }
}