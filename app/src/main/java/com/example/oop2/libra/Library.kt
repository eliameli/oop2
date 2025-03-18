package com.example.oop2.libra

import com.example.oop2.models.Book
import com.example.oop2.models.Newspaper
import com.example.oop2.models.Disk
import com.example.oop2.models.LibraryItem
import com.example.oop2.LibraryAction

class Library(private val items: List<LibraryItem>) {
    fun start() {
        while (true) {
            println("Выберите тип объекта:\n1. Показать книги\n2. Показать газеты\n3. Показать диски\n0. Выход")
            when (readLine()?.toIntOrNull()) {
                1 -> showItems(items.filterIsInstance<Book>())
                2 -> showItems(items.filterIsInstance<Newspaper>())
                3 -> showItems(items.filterIsInstance<Disk>())
                0 -> return
                else -> println("Неверный выбор.")
            }
        }
    }
    private fun <T : LibraryItem> showItems(items: List<T>) {
        items.forEachIndexed { index, item -> println("${index + 1}. ${item.getBriefInfo()}") }
        println("Выберите объект (номер) или 0 для возврата в меню:")
        val objectNumber = readLine()?.toIntOrNull()

        if (objectNumber == 0) return

        if (objectNumber != null && objectNumber in 1..items.size) {
            performAction(items[objectNumber - 1] as LibraryAction)  // приход к экшена
        } else {
            println("Неверный номер объекта.")
        }
    }
    // Выполнить действие
    private fun performAction(item: LibraryAction) {
        println("Выберите действие:\n1. Взять домой\n2. Читать в читальном зале\n3. Показать подробную информацию\n4. Вернуть\n0. Вернуться в меню")
        when (readLine()?.toIntOrNull()) {
            1 -> item.takeHome()
            2 -> item.readInHall()
            3 -> println(item.getDetailedInfo())
            4 -> item.returnItem()
            0 -> return
            else -> println("Неверный выбор.")
        }
    }
}