package com.bp.twitter.domain

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class GroupActionLoadList<T>(
        val pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>,
        val initialState: LiveData<NetworkState>
)