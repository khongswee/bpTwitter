package com.bp.twitter.di

import android.app.Application
import com.bp.twitter.BPTwitterApplication
import com.bp.twitter.data.tweetsearch.di.SearchRepositoryModule
import com.bp.twitter.domain.di.ServiceDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBinder::class,
    SearchRepositoryModule::class,
    ServiceDataModule::class])
interface AppComponent {
    fun inject(app: BPTwitterApplication)

    @Component.Builder
    interface Builder : ComponentBuilder<AppComponent> {
        @BindsInstance
        fun application(application: Application): Builder
    }

}

interface ComponentBuilder<out C> {
    fun build(): C
}
