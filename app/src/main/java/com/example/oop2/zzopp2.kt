package com.example.oop2
import com.example.oop2.models.Book
import com.example.oop2.models.Newspaper
import com.example.oop2.models.Disk
import com.example.oop2.libra.Library
import com.example.oop2.models.LibraryItem
fun main() {
    // создание элементов
    val items: MutableList<LibraryItem> = mutableListOf(
        Book(1, true, "Маугли", "Джозеф Киплинг", 202),
        Book(11, true, "Звездные войны", "Джордж Лукас", 401),
        Newspaper(2, true, "Сельская жизнь",  794, 3),
        Newspaper(21, true, "Семья",   23, 12),
        Disk(3, true, "Дэдпул и Росомаха", "DVD"),
        Disk(31, true, "Один Дома", "CD")
    )

    // запуск
    Library(items).start()
}



