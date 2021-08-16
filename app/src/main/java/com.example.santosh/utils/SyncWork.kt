package com.example.santosh.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.example.santosh.data.database.AppDatabase
import com.example.santosh.data.dinjection.AppModule
import com.example.santosh.data.remoteclass.ItemService
import kotlinx.coroutines.runBlocking

class SyncWork(ctx: Context, workerParams: WorkerParameters) : Worker(ctx, workerParams) {

    override fun doWork(): Result {
        val appContext = applicationContext
        return try {
            runBlocking {
                AppModule.provideRetrofit(Gson()).create(ItemService::class.java).getAllRepo().let { AppDatabase.getDatabase(appContext).itemDao().insertAll(it.body()!!.items) }
            }
            Result.success()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.failure()
        }
    }
}