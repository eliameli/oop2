package com.example.oop2.libra
import com.example.oop2.R


import com.example.oop2.models.*

import com.example.oop2.models.LibraryItem
import com.example.oop2.LibraryAction
import com.example.oop2.stores.DigitizationCabinet
import com.example.oop2.stores.Manager
import com.example.oop2.stores.BookStore

import com.example.oop2.stores.DiskStore
import com.example.oop2.stores.NewspaperStore


class Library(private val items: MutableList<LibraryItem>) {

    private val manager = Manager()  // экземпляр менеджера
    private val digitizationCabinet = DigitizationCabinet<LibraryItem, Disk>()
    fun start() {
        while (true) {
            println("Выберите действие:\n1. Показать объекты\n2. Купить объект\n3. Оцифровать \n0. Выход\n\n")
            when (readLine()?.toIntOrNull()) {
                1 -> showItems(items)
                2 -> purchaseItem()  // покупка
                3 -> digitizeItem() // оцифровка
                0 -> return
                else -> println("Неверный выбор.\n")
            }
        }
    }

    private fun <T : LibraryItem> showItems(items: List<T>) {
        if (items.isEmpty()) {
            println("Библиотека пуста.\n\n")
            return
        }

        items.forEachIndexed { index, item -> println("${index + 1}. ${item.getBriefInfo()}") }
        println("Выберите объект (номер) или 0 для возврата в меню:\n\n")

        val objectNumber = readLine()?.toIntOrNull()
        if (objectNumber == 0) return

        if (objectNumber != null && objectNumber in 1..items.size) {
            performAction(items[objectNumber - 1] as LibraryAction)
        } else {
            println("Неверный номер объекта.\n\n")
        }
    }





    // Выполнить действие
    private fun performAction(item: LibraryAction) {
        println("Выберите действие:\n1. Взять домой\n2. Читать в читальном зале\n3. Показать подробную информацию\n4. Вернуть\n0. Вернуться в меню\n\n")
        when (readLine()?.toIntOrNull()) {
            1 -> item.takeHome()
            2 -> item.readInHall()
            3 -> println(item.getDetailedInfo())
            4 -> item.returnItem()
            0 -> return
            else -> println("Неверный выбор.\n\n")
        }
    }

    private fun purchaseItem() {
        println("Выберите:\n1. Магазин книг\n2. Магазин дисков\n3. Газетный ларек\n0. Назад\n\n")

        when (readLine()?.toIntOrNull()) {
            1 -> {

                val book = manager.buy(BookStore())
                println("Куплена книга: ${book.name}\n")
            }
            2 -> {
                val disk = manager.buy(DiskStore())
                println("Куплен диск: ${disk.name}\n")
            }
            3 -> {
                val newspaper = manager.buy(NewspaperStore())
                println("Куплена газета: ${newspaper.name}\n")
            }
            0 -> return
            else -> println("Неверный выбор.\n\n")
        }
        return

    }



    private fun digitizeItem() {
        println("Выберите объект для оцифровки (номер) или 0 для возврата в меню:")
        items.forEachIndexed { index, item -> println("${index + 1}. ${item.getBriefInfo()}") }
        val objectNumber = readLine()?.toIntOrNull()

        if (objectNumber != null && objectNumber in 1..items.size) {
            val selectedItem = items[objectNumber - 1]

            if (selectedItem is Book || selectedItem is Newspaper) {
                val disk = digitizationCabinet.digitize(selectedItem)
                items.add(disk)
                println("Оцифровка завершена! Новый диск добавлен в библиотеку: ${disk.getBriefInfo()}")
            } else {
                println("Этот объект нельзя оцифровать!")
            }
        } else {
            println("Неверный номер объекта.")
        }
    }



}