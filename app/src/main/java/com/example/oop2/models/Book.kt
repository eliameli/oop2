package com.example.oop2.models
import com.example.oop2.R
import java.io.Serializable

class Book(
    id: Int,
    isAvailable: Boolean,
    name: String,
    val author: String,
    val pages: Int
) : LibraryItem(id, isAvailable, name), Serializable {
    override val iconResId: Int get() = R.drawable.ic_book
    override val type: String = "Book"
    override fun getBriefInfo(): String = "Книга $name — ${if (isAvailable) "Доступна" else "Нет"}"
}

//    override fun getBriefInfo(): String =
//        "Книга $name — ${if (isAvailable) "Доступна" else "Нет"}"
//
//    override fun getDetailedInfo(): String =
//        "Книга '$name' автора $author\nСтраниц: $pages\nID: $id"
//
//    override fun takeHome() {
//        if (isAvailable) {
//            isAvailable = false
//            println("Книгу $name взяли домой.")
//        } else {
//            println("Книга $name недоступна для взятия домой.")
//        }
//    }
//
//    override fun readInHall() {
//        println("Чтение книги $name в зале.")
//    }
//
//    override fun returnItem() {
//        if (!isAvailable) {
//            isAvailable = true
//            println("Книга $name возвращена.")
//        } else {
//            println("Книга $name уже доступна.")
//        }
//    }