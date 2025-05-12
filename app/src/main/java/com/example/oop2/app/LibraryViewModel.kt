package com.example.oop2.app

import androidx.lifecycle.*
import com.example.oop2.R
import com.example.oop2.common.DiskType
import com.example.oop2.data.local.LibraryItemEntity
import com.example.oop2.domain.model.*
import com.example.oop2.domain.usecase.AddLibraryItemUseCase
import com.example.oop2.domain.usecase.GetLibraryItemsUseCase
import com.example.oop2.domain.usecase.SearchGoogleBooksUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val addItemUseCase: AddLibraryItemUseCase,
    private val getItemsUseCase: GetLibraryItemsUseCase,
    private val searchGoogleBooksUseCase: SearchGoogleBooksUseCase
) : ViewModel() {

    private val _libraryItems = MutableLiveData<List<LibraryItem>>()
    val libraryItems: LiveData<List<LibraryItem>> = _libraryItems

    private val _googleBooks = MutableLiveData<List<Book>>()
    val googleBooks: LiveData<List<Book>> = _googleBooks

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun addItem(item: LibraryItem) {
        viewModelScope.launch {
            try {
                addItemUseCase(item)
                refreshItems()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun refreshItems() {
        viewModelScope.launch {
            _loading.value = true
            delay(1000)
            try {
                val items = getItemsUseCase.getSortedByName(limit = 20, offset = 0)
                _libraryItems.value = items

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun searchGoogleBooks(author: String, title: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = searchGoogleBooksUseCase(author, title)
                _googleBooks.value = result
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveGoogleBook(book: Book) {
        addItem(book)
    }

    fun loadInitialItems() {


        viewModelScope.launch {
            _loading.value = true
            try {
                val items = getItemsUseCase()
                _libraryItems.value = items
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearGoogleBooks() {
        _googleBooks.value = emptyList()
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
