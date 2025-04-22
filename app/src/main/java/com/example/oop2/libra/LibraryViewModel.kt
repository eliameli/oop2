package com.example.oop2.libra
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.oop2.models.LibraryItem
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LibraryViewModel : ViewModel() {
    private val _items = MutableLiveData<List<LibraryItem>>()
    val items: LiveData<List<LibraryItem>> get() = _items

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private var accessCount = 0
    fun addItem(item: LibraryItem) {
        LibraryRepository.addItem(item)
        loadItems(showDelay = false)
    }


    fun loadItems(showDelay: Boolean = true) {
        viewModelScope.launch {
            _items.value = emptyList()
            if (showDelay) delay(1000L)

            try {
                delay((100..2000).random().toLong())
                accessCount++
                if ((1..5).random() == 3) {
                    throw RuntimeException("Ошибка чтения из базы")
                }
                val data = LibraryRepository.getAllItems()
                _items.value = data
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }}

