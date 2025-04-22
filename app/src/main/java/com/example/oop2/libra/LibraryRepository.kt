package com.example.oop2.libra

import com.example.oop2.models.*
import com.example.oop2.libra.LibraryViewModel
object LibraryRepository {
    private val items = mutableListOf<LibraryItem>()

    fun getAllItems(): List<LibraryItem> {
        if (items.isEmpty()) {
            getInitialData()
        }
        return items
    }


    fun addItem(item: LibraryItem) {
        items.add(item)
    }

    fun removeItem(item: LibraryItem) {
        items.remove(item)
    }

    fun getInitialData(): List<LibraryItem> {
        if (items.isEmpty()) {
            val books = listOf(
                Book(1, true, "Маугли", "Джозеф Киплинг", 202),
                Book(11, true, "Звездные войны, Часть 1", "Джордж Лукас", 401),
                Book(12, true, "Звездные войны, Часть 2", "Джордж Лукас", 412),
                Book(13, true, "Звездные войны, Часть 3", "Джордж Лукас", 441),
                Book(14, true, "Звездные войны, Часть 4", "Джордж Лукас", 423),
                Book(15, true, "Звездные войны, Часть 5", "Джордж Лукас", 363),
                Book(16, true, "Звездные войны, Часть 6", "Джордж Лукас", 621),
                Book(17, true, "Робинзон Крузо", "Даниель Дефо", 666)
            )
            val disks = listOf(
                Disk(3, true, "Дэдпул и Росомаха", DiskType.CD),
                Disk(31, true, "Один Дома", DiskType.DVD)
            )
            val newspapers = listOf(
                Newspaper(2, true, "Сельская жизнь", 794, 3),
                Newspaper(21, true, "Семья", 23, 12)
            )
            items.addAll(books + disks + newspapers)
        }
        return items
    }
}
