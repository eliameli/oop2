package com.example.oop2.stores

import com.example.oop2.domain.model.LibraryItem


interface Store<T : LibraryItem> {
    fun sell(): T  // метод продажи объекта типа T
}
