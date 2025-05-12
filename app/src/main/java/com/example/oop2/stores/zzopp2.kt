package com.example.oop2.stores
import com.example.oop2.domain.model.Book
import com.example.oop2.domain.model.Disk
import com.example.oop2.common.DiskType
import com.example.oop2.domain.libra.Library
import com.example.oop2.domain.model.LibraryItem
import com.example.oop2.domain.model.Newspaper

fun main() {
    // создание элементов
    val items: MutableList<LibraryItem> = mutableListOf(
        Book(1, true, "Маугли", "Джозеф Киплинг", 202),
        Book(11, true, "Звездные войны", "Джордж Лукас", 401),
        Newspaper(2, true, "Сельская жизнь",  794, 3),
        Newspaper(21, true, "Семья",   23, 12),
        Disk(3, true, "Дэдпул и Росомаха", DiskType.CD),
        Disk(31, true, "Один Дома", DiskType.DVD)
    )
    // запуск
    Library(items).start()
}



