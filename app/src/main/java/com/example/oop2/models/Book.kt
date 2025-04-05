package com.example.oop2.models
import com.example.oop2.LibraryAction

import com.example.oop2.R

class Book(
    id: Int,
    isAvailable: Boolean,
    name: String,
    private val author: String,
    private val pages: Int
) : LibraryItem(id, isAvailable, name), LibraryAction {

    override fun getIconResId(): Int = R.drawable.ic_book

    override fun getBriefInfo(): String =
        "$name (Автор: $author) — ${if (isAvailable) "Доступна" else "Нет"}"

    override fun getDetailedInfo(): String =
        "Книга '$name' автора $author, $pages страниц"

    override fun takeHome() {
        if (isAvailable) {
            isAvailable = false
            println("$name взяли домой.")
        } else {
            println("$name недоступна для взятия домой.")
        }
    }

    override fun readInHall() {
        if (isAvailable) {
            isAvailable = false
            println("$name можно читать в читальном зале.")
        } else {
            println("$name недоступна для чтения в зале.")
        }
    }

    override fun returnItem() {
        if (!isAvailable) {
            isAvailable = true
            println("$name возвращена.")
        } else {
            println("$name уже доступна.")
        }
    }
}
