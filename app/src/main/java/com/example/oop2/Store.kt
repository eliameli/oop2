package com.example.oop2

import com.example.oop2.models.LibraryItem


interface Store<T : LibraryItem> {
    fun sell(): T  // метод продажи объекта типа T
}
