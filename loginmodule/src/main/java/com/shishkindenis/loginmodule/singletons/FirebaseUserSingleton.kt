package com.shishkindenis.loginmodule.singletons

import com.google.firebase.auth.FirebaseAuth

object FirebaseUserSingleton {
    private val auth = FirebaseAuth.getInstance()
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