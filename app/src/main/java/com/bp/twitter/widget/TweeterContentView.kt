package com.bp.twitter.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bp.twitter.R
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.view_tweet_content.view.*
import kotlin.properties.Delegates
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.util.Log


/**
 * Created by Administrator on 10/20/18.
 */
class TweeterContentView : FrameLayout {

    var tweetContent by Delegates.observable<TweetDisplayModel?>(null) { _, _, new ->
        new?.let {
            setContent(it)
        }
    }

    constructor(context: Context) : super(context) {
        initInflate()

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initInflate()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initInflate()
    }


    private fun initInflate() {
        View.inflate(context, R.layout.view_tweet_content, this)
    }

    private fun setContent(tweet: TweetDisplayModel) {
        if (tweet.isReTweet) {
            viewRetweet.visibility = View.VISIBLE
        } else {
            viewRetweet.visibility = View.GONE
        }

        val hashtagintitle = SpannableString(tweet.text)
        Log.d("dfdfdfdf",tweet.hashTags.toString())
        tweet.hashTags.forEach {
            hashtagintitle.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorSecondary))
                    ,it.indices.first(), it.indices.last(), 0)
        }
        tvTexttweet.text = hashtagintitle
        tvAccount.text = tweet.namePost
        tvusername.text = tweet.screenNamePost
        tvAccountRetweet.text = tweet.nameRetweet
        tvTime.text = tweet.timePost

        Glide.with(context).load(tweet.profileImage)
                .apply(RequestOptions.circleCropTransform()).into(imgProfile)
    }

}