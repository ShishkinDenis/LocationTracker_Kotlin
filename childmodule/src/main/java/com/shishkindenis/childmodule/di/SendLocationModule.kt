package com.shishkindenis.childmodule.di

import com.shishkindenis.childmodule.sendLocation.view.SendLocationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SendLocationModule {

        @ContributesAndroidInjector()
        abstract fun provideSendLocationActivity(): SendLocationActivity

}