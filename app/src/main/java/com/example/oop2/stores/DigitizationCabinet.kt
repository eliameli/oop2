package com.example.oop2.stores

import com.example.oop2.models.Book
import com.example.oop2.models.Newspaper
import com.example.oop2.models.LibraryItem
import com.example.oop2.models.Disk

class DigitizationCabinet {
    companion object {
        fun <T : LibraryItem> digitize(item: T): Disk {
            val diskName = when (item) {
                is Book -> "Цифровая копия книги: ${item.name}"
                is Newspaper -> "Цифровая копия газеты: ${item.name}"
                else -> throw IllegalArgumentException("Можно оцифровывать только книги и газеты.")
            }
            return Disk(id = item.id + 1000, isAvailable = true, name = diskName, diskType = "CD")
        }
    }
}