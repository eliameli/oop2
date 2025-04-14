package com.example.oop2.libra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop2.models.LibraryItem

class LibraryViewModel : ViewModel() {
    private val _items = MutableLiveData<List<LibraryItem>>()
    val items: LiveData<List<LibraryItem>> get() = _items

    init {
        preloadItems()
    }
    fun preloadItems() {
        if (LibraryRepository.getItems().isEmpty()) {
            _items.value = LibraryRepository.getInitialData()
        } else {
            _items.value = LibraryRepository.getItems()
        }
    }
    fun addItem(item: LibraryItem) {
        LibraryRepository.addItem(item)
        _items.value = LibraryRepository.getItems()
    }
    fun removeItem(item: LibraryItem) {
        LibraryRepository.removeItem(item)
        _items.value = LibraryRepository.getItems()
    }
    fun refresh() {
        _items.value = LibraryRepository.getItems()
    }
}
