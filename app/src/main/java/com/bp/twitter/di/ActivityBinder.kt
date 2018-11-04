package com.bp.twitter.di

import com.bp.twitter.presentation.di.SearchListActivityModule
import com.bp.twitter.presentation.tweetlist.view.SearchListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinder {
    @ContributesAndroidInjector(modules = [SearchListActivityModule::class])
    abstract fun bindSearchListActivity(): SearchListActivity

}