package com.shishkindenis.parentmodule.singletons

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class FirebaseUserSingleton @Inject constructor() {
    //class FirebaseUserSingleton  constructor() {
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