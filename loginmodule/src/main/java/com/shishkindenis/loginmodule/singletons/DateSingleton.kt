package com.shishkindenis.locationtracker_kotlin.singletons

import javax.inject.Inject

class DateSingleton @Inject constructor() {
    private var date: String? = null

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }
}