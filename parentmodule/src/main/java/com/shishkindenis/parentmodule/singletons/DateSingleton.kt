package com.shishkindenis.parentmodule.singletons

object DateSingleton {
    private var date: String? = null

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }
}