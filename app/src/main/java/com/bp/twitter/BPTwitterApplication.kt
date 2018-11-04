package com.bp.twitter

import android.app.Activity
import android.app.Application
import com.bp.twitter.di.AppComponent
import com.bp.twitter.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BPTwitterApplication : Application(),HasActivityInjector{
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjection
    }

    @Inject
    lateinit var activityDispatchingAndroidInjection: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initModuleCoponent()
    }

    fun initModuleCoponent() {

        val appComponent  = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

}