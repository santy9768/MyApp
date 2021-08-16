package com.example.santosh

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.santosh.utils.SyncWork
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val myWorkBuilder: PeriodicWorkRequest.Builder = PeriodicWorkRequest.Builder(SyncWork::class.java, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
        val myWork: PeriodicWorkRequest = myWorkBuilder.build()
        val mWorkManager = WorkManager.getInstance(applicationContext)
        mWorkManager.enqueue(myWork)
    }
}