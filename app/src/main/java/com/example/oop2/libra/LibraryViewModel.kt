package com.example.oop2.libra

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.oop2.database.LibraryItemEntity
import com.example.oop2.models.*
import com.example.oop2.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = application.getSharedPreferences("library_prefs", Context.MODE_PRIVATE)

    private val _items = MutableLiveData<List<LibraryItemEntity>>()
    val items: LiveData<List<LibraryItemEntity>> get() = _items

    private val _libraryItems = MutableLiveData<List<LibraryItem>>()
    val libraryItems: LiveData<List<LibraryItem>> get() = _libraryItems

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private var currentOffset = 0
    private var pageSize = 30
    private var isLoading = false

    private var sortByName = preferences.getBoolean(KEY_SORT_BY_NAME, true)

    companion object {
        private const val KEY_SORT_BY_NAME = "sort_by_name"
    }

    private var accessCount = 0

    init {
        loadInitialItems()
    }

    fun loadInitialItems() {
        viewModelScope.launch {
            isLoading = true

            _items.value = emptyList()

            delay(1000L) // показываем shimmer хотя бы секунду

            try {
                delay((100..2000).random().toLong())

                LibraryRepository.initializeIfEmpty()

                accessCount++
                if (accessCount % 4 == 0) {
                    throw RuntimeException("Ошибка чтения из базы")
                }

                val loadedItems = if (sortByName) {
                    LibraryRepository.getItemsSortedByName(pageSize, 0)
                } else {
                    LibraryRepository.getItemsSortedByDate(pageSize, 0)
                }

                _items.value = loadedItems
                _libraryItems.value = loadedItems.map { entityToModel(it) }
                currentOffset = loadedItems.size
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreItems() {
        if (isLoading) return
        viewModelScope.launch {
            isLoading = true
            try {
                val loadedItems = if (sortByName) {
                    LibraryRepository.getItemsSortedByName(pageSize / 2, currentOffset)
                } else {
                    LibraryRepository.getItemsSortedByDate(pageSize / 2, currentOffset)
                }
                val currentList = _items.value.orEmpty()
                _items.value = currentList + loadedItems
                _libraryItems.value = _items.value!!.map { entityToModel(it) }
                currentOffset += loadedItems.size
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun refreshItems() {
        currentOffset = 0
        loadInitialItems()
    }

    fun changeSorting(sortByNameSelected: Boolean) {
        sortByName = sortByNameSelected
        preferences.edit().putBoolean(KEY_SORT_BY_NAME, sortByName).apply()
        refreshItems()
    }

    fun addItem(item: LibraryItemEntity) {
        viewModelScope.launch {
            try {
                LibraryRepository.insert(item)
                refreshItems()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun entityToModel(entity: LibraryItemEntity): LibraryItem {
        return when (entity.type) {
            "Book" -> Book(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                author = entity.author ?: "Неизвестно",
                pages = entity.pages ?: 0,
                iconResId = R.drawable.ic_book
            )
            "Disk" -> Disk(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                diskType = entity.diskType?.let { DiskType.valueOf(it) } ?: DiskType.CD,
                iconResId = R.drawable.ic_disk
            )
            "Newspaper" -> Newspaper(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                issueNumber = entity.issueNumber ?: 0,
                month = entity.month ?: 1,
                iconResId = R.drawable.ic_newspaper
            )
            else -> Book(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                author = "Неизвестно",
                pages = 0,
                iconResId = R.drawable.ic_book
            )
        }
    }
}
