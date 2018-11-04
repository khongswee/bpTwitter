package com.bp.twitter.presentation.tweetgallery.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.bp.twitter.presentation.tweetgallery.view.TweetImagePageFragment

class TweetImageViewpagerAdapter internal constructor(fm: FragmentManager, val listMedia:ArrayList<String>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return TweetImagePageFragment.newIntance(listMedia[position])
        }

        override fun getCount(): Int {
            return listMedia.size
        }
    }

