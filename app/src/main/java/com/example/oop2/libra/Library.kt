package com.example.oop2.libra

import com.example.oop2.models.Book
import com.example.oop2.models.Newspaper
import com.example.oop2.models.Disk
import com.example.oop2.models.LibraryItem
import com.example.oop2.LibraryAction
import com.example.oop2.stores.DigitizationCabinet
import com.example.oop2.stores.Manager
import com.example.oop2.stores.BookStore
import com.example.oop2.stores.DiskStore
import com.example.oop2.stores.NewspaperStore


class Library(private val items: List<LibraryItem>) {
    private val manager = Manager()  // Создаем экземпляр менеджера

    fun start() {
        while (true) {
            println("Выберите действие:\n1. Показать объекты\n2. Купить объект\n3. Оцифровать \n0. Выход")
            when (readLine()?.toIntOrNull()) {
                1 -> showItems(items)
                2 -> purchaseItem()  // покупка
                3 -> digitizeItem() // оцифровка
                0 -> return
                else -> println("Неверный выбор.")
            }
        }
    }

    private fun <T : LibraryItem> showItems(items: List<T>) {
        if (items.isEmpty()) {
            println("Библиотека пуста.")
            return
        }

        items.forEachIndexed { index, item -> println("${index + 1}. ${item.getBriefInfo()}") }
        println("Выберите объект (номер) или 0 для возврата в меню:")

        val objectNumber = readLine()?.toIntOrNull()
        if (objectNumber == 0) return

        if (objectNumber != null && objectNumber in 1..items.size) {
            performAction(items[objectNumber - 1] as LibraryAction)
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

    private fun purchaseItem() {
        println("Выберите:\n1. Магазин книг\n2. Магазин дисков\n3. Газетный ларек\n0. Назад")

        when (readLine()?.toIntOrNull()) {
            1 -> {

                val book = manager.buy(BookStore())
                println("Куплена книга: ${book.name}")
            }
            2 -> {
                val disk = manager.buy(DiskStore())
                println("Куплен диск: ${disk.name}")
            }
            3 -> {
                val newspaper = manager.buy(NewspaperStore())
                println("Куплена газета: ${newspaper.name}")
            }
            0 -> return
            else -> println("Неверный выбор.")
        }
        return

    }





    private fun digitizeItem() {
        val booksAndNewspapers =
            items.filterIsInstance<Book>() + items.filterIsInstance<Newspaper>()

        if (booksAndNewspapers.isEmpty()) {
            println("Нет доступных книг или газет для оцифровки.")
            return
        }

        println("Выберите объект для оцифровки:")
        booksAndNewspapers.forEachIndexed { index, item -> println("${index + 1}. ${item.getBriefInfo()}") }

        val choice = readLine()?.toIntOrNull()
        if (choice == null || choice !in 1..booksAndNewspapers.size) {
            println("Неверный выбор.")
            return
        }

        val digitizationCabinet = DigitizationCabinet() // Создаем объект



        val selectedItem = booksAndNewspapers[choice - 1]
        val disk = DigitizationCabinet.digitize(selectedItem)
        println("Оцифровка завершена! Создан цифровой носитель: ${disk.getBriefInfo()}")


    }

}