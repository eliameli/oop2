package com.example.oop2.models
import com.example.oop2.LibraryAction

class Book(
    id: Int,
    isAvailable: Boolean,
    name: String,
    val author: String,
    val pages: Int
) : LibraryItem(id, isAvailable, name), LibraryAction {

    // то что забыл почему то
    override fun getBriefInfo(): String = "$name доступна: ${if (isAvailable) "Да" else "Нет"}"


    override fun getDetailedInfo(): String =
        "Книга: $name ($pages стр.) автора: $author с id: $id доступна: ${if (isAvailable) "Да" else "Нет"}"


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
            println("$name возвращен.")
        } else {
            println("$name уже доступна.")
        }
    }
}
