package com.shishkindenis.loginmodule.singleton

import com.google.firebase.auth.FirebaseAuth

object FirebaseUserSingleton {

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private var userId: String? = null

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getFirebaseAuth(): FirebaseAuth? {
        return auth
    }
}