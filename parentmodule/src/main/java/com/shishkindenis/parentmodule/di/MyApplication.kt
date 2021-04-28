package com.shishkindenis.parentmodule.di

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MyApplication> =
            DaggerApplicationComponent.builder()
                    .create(this)

}