package com.example.santosh.data.dinjection

import android.content.Context

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.santosh.data.database.AppDatabase
import com.example.santosh.data.database.ItemDao
import com.example.santosh.data.remoteclass.ItemRemoteDataSource
import com.example.santosh.data.remoteclass.ItemService
import com.example.santosh.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideItemService(retrofit: Retrofit): ItemService = retrofit.create(ItemService::class.java)

    @Singleton
    @Provides
    fun provideItemRemoteDataSource(itemService: ItemService) = ItemRemoteDataSource(itemService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideItemDao(db: AppDatabase) = db.itemDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ItemRemoteDataSource,
                          localDataSource: ItemDao) =
        ItemRepository(remoteDataSource, localDataSource)
}