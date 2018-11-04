package com.bp.twitter.injection

import android.app.Application
import com.bp.twitter.TestApplication
import com.bp.twitter.data.tweetsearch.di.TestSearchRepositoryModule
import com.bp.twitter.di.ActivityBinder
import com.bp.twitter.di.AppComponent
import com.bp.twitter.di.AppModule
import com.bp.twitter.domain.tweetsearch.SearchListRepository
import com.bp.twitter.module.TestServiceDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    TestSearchRepositoryModule::class,
    ActivityBinder::class,
    TestServiceDataModule::class])
interface TestApplicationComponent : AppComponent{

    fun searchRepository2(): SearchListRepository
    fun inject(app: TestApplication)

    @Component.Builder
    interface Builder : ComponentBuilder<TestApplicationComponent> {
        @BindsInstance
        fun application(application: Application): Builder
    }


}

interface ComponentBuilder<out C> {
    fun build(): C
}
