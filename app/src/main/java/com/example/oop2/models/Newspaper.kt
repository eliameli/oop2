package com.example.oop2.models
import com.example.oop2.LibraryAction

class Newspaper(
    id: Int,
    isAvailable: Boolean,
    name: String,
    val issueNumber: Int,
    val Month: Int
) : LibraryItem(id, isAvailable, name), LibraryAction {


    private fun getMonthName(month: Int): String {
        val months = listOf(
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        )
        // так как в котлине все исчисления начинаются с 0 то делаем month -1
        return if (month in 1..12) months[month - 1] else "Неверный месяц"
    }



    override fun getBriefInfo(): String = "$name доступна: ${if (isAvailable) "Да" else "Нет"}"

    override fun getDetailedInfo(): String =
        "Выпуск: $issueNumber газеты $name с id: $id выпущена в месяце ${getMonthName(Month)} доступна: ${if (isAvailable) "Да" else "Нет"}"

    override fun takeHome() {
        println("Газету $name нельзя взять домой.")
    }

    override fun readInHall() {
        if (isAvailable) {
            isAvailable = false
            println("Газету $name можно читать в читальном зале.")
        } else {
            println("Газета $name недоступна для чтения в зале.")
        }
    }

    override fun returnItem() {
        if (!isAvailable) {
            isAvailable = true
            println("Газета $name возвращена.")
        } else {
            println("Газета $name уже доступна.")
        }
    }
}
