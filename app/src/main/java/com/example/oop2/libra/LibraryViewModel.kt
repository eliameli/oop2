package com.example.oop2.libra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop2.R
import com.example.oop2.database.LibraryItemEntity
import com.example.oop2.models.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LibraryViewModel : ViewModel() {

    private val _libraryItems = MutableLiveData<List<LibraryItem>>()
    val libraryItems: LiveData<List<LibraryItem>> get() = _libraryItems

    private val _googleBooks = MutableLiveData<List<LibraryItem>>()
    val googleBooks: LiveData<List<LibraryItem>> get() = _googleBooks

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun addItem(entity: LibraryItemEntity) {
        viewModelScope.launch {
            try {
                LibraryRepository.insert(entity)
                loadInitialItems()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadInitialItems() {
        viewModelScope.launch {
            _loading.value = true
            delay(1000) // гарантированный шиммер
            try {
                LibraryRepository.initializeIfEmpty()
                val entities = LibraryRepository.getItemsSortedByName(30, 0)
                _libraryItems.value = entities.map { entityToModel(it) }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun refreshItems() {
        loadInitialItems()
    }

    fun clearGoogleBooks() {
        _googleBooks.value = emptyList()
    }

    fun searchGoogleBooks(author: String, title: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val books = GoogleBooksRepository.searchBooks(author, title)
                _googleBooks.value = books
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveGoogleBook(book: Book) {
        viewModelScope.launch {
            try {
                val entity = LibraryItemEntity(
                    name = book.name,
                    type = "Book",
                    author = book.author,
                    pages = book.pages,
                    diskType = null,
                    issueNumber = null,
                    month = null
                )
                LibraryRepository.insert(entity)
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
