package com.example.santosh.data.repository

import com.example.santosh.data.database.ItemDao
import com.example.santosh.data.remoteclass.ItemRemoteDataSource
import com.example.santosh.utils.performGetOperation
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val remoteDataSource: ItemRemoteDataSource,
    private val localDataSource: ItemDao
) {

    fun getItems() = performGetOperation(
        databaseQuery = { localDataSource.getAllItems() },
        networkCall = { remoteDataSource.getItems() },
        saveCallResult = { localDataSource.insertAll(it.items) }
    )

    fun getItems(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getItems(id) },
    )

}