package com.example.oop2

import com.example.oop2.models.LibraryItem

// Интерфейс магазина с обобщениями
interface Store<T : LibraryItem> {
    fun sell(): T  // Метод продажи объекта типа T
}
