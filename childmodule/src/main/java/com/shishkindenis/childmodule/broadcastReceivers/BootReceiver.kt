package com.shishkindenis.childmodule.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.childmodule.workers.LocationWorker
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton

class BootReceiver : BroadcastReceiver() {
    private var user: FirebaseUser? = null

    override fun onReceive(context: Context, intent: Intent) {
        user = FirebaseUserSingleton.getFirebaseAuth()?.currentUser
        if (user != null) {
            val myWorkRequest = OneTimeWorkRequest.Builder(LocationWorker::class.java).build()
            WorkManager.getInstance().enqueue(myWorkRequest)
        }
    }
}