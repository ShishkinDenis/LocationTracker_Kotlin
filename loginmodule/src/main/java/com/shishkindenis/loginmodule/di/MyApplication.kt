package com.shishkindenis.loginmodule.di

import android.app.Application


class MyApplication : Application() {
    companion object {
        val appComponent: AppComponent = DaggerAppComponent.create()
    }
}