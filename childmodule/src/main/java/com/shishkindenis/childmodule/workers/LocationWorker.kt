package com.shishkindenis.childmodule.workers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shishkindenis.childmodule.services.ForegroundService


class LocationWorker : Worker() {
    private var serviceIntent: Intent? = null

    fun LocationWorker(
        context: Context,
        workerParams: WorkerParameters
    ) {
        super(context, workerParams)
        serviceIntent = Intent(context, ForegroundService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent!!)
    }

    override fun doWork(): Result {
        return Result.success()
    }
}