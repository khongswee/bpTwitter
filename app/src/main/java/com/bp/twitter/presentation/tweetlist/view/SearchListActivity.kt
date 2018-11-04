package com.bp.twitter.presentation.tweetlist.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bp.twitter.extension.observe
import com.bp.twitter.R
import com.bp.twitter.domain.NetworkState
import com.bp.twitter.domain.Status
import com.bp.twitter.presentation.tweetgallery.view.TweetImageGalleryActivity
import com.bp.twitter.presentation.tweetlist.view.adapter.SearchTweetAdapter
import com.bp.twitter.presentation.tweetlist.viewmodel.SearchListViewModel
import com.bp.twitter.presentation.tweetlist.viewmodel.SearchViewModelFactory
import com.hlab.animatedPullToRefresh.AnimatedPullToRefreshLayout
import dagger.android.AndroidInjection
import im.ene.toro.PlayerSelector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class SearchListActivity : AppCompatActivity(), AnimatedPullToRefreshLayout.OnRefreshListener, SearchTweetAdapter.OnTweetPhotoClick {

    @Inject
    lateinit var viewmodelFactory: SearchViewModelFactory

    private var adapterList: SearchTweetAdapter? = null

    private lateinit var viewModel: SearchListViewModel

    private val STATE_LOADING = 0
    private val STATE_ERROR = 1
    private val STATE_CONTENT = 2

    private var hasInit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewmodelFactory).get(SearchListViewModel::class.java)
        initAdapter()
        getAllPosts()
        pull_to_refresh.setColorAnimationArray(intArrayOf(Color.parseColor("#000000")))
        pull_to_refresh.setOnRefreshListener(this)

        reloadButton.setOnClickListener {
            viewModel.reload()
        }


    }

    private fun getAllPosts() {
        observe(viewModel.tweetList) {
            adapterList?.submitList(it)
        }

        observe(viewModel.networkState) {
            adapterList?.setNetworkState(it)
            handleStateView(it, false)
        }

        observe(viewModel.initialState) {
            adapterList?.setNetworkState(it)
            handleStateView(it, true)
        }

    }

    private fun handleStateView(state: NetworkState, isInitState: Boolean) {
        when (state.status) {
            Status.FAILED -> {
                if (isInitState && !hasInit) {
                    view_flipper.displayedChild = STATE_ERROR
                } else {
                    Toast.makeText(this, "Could not load data.", Toast.LENGTH_SHORT).show()
                }
            }
            Status.SUCCESS -> {
                hasInit = true
                pull_to_refresh.refreshComplete()
                view_flipper.displayedChild = STATE_CONTENT
            }

            Status.RUNNING -> {
                if (isInitState && !hasInit) {
                    view_flipper.displayedChild = STATE_LOADING
                }
            }
        }
    }

    private fun initAdapter() {

        adapterList = SearchTweetAdapter().apply { listTweetPhoto = this@SearchListActivity }
        val layoutManager = LinearLayoutManager(this)
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)
        rvTweet.addItemDecoration(itemDecorator)
        rvTweet.playerSelector = PlayerSelector.DEFAULT
        rvTweet.layoutManager = layoutManager
        rvTweet.adapter = adapterList
    }

    override fun onClickPhoto(list: List<String>, position: Int) {
        val intent = Intent(this@SearchListActivity, TweetImageGalleryActivity::class.java)
        val arrayUrl = arrayListOf<String>()
        list.forEach {
            arrayUrl.add(it)
        }
        intent.putExtra("list", arrayUrl)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    override fun onRefresh() {
        viewModel.reload()
    }

}
