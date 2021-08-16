package com.example.santosh.data.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.santosh.data.entities.Item
import com.example.santosh.data.repository.ItemRepository
import com.example.santosh.utils.Resource

class ItemDetailViewModel @ViewModelInject constructor(private val repository: ItemRepository) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _items = _id.switchMap { id ->
        repository.getItems(id)
    }
    val items: LiveData<Resource<Item>> = _items


    fun start(id: Int) {
        _id.value = id
    }

}
