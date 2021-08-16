package com.example.santosh.data.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.santosh.data.repository.ItemRepository

class ItemViewModel @ViewModelInject constructor(private val repository: ItemRepository): ViewModel(){
    val items= repository.getItems()
}