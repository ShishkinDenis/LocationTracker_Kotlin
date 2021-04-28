package com.shishkindenis.parentmodule.singleton

object DateSingleton {
    private var date: String? = null

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }
}