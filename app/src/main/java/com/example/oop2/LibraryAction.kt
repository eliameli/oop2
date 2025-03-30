package com.example.oop2
import com.example.oop2.R
interface LibraryAction {
    fun getBriefInfo(): String  // кратко
    fun getDetailedInfo(): String  // подробно
    // действия
    fun takeHome()
    fun readInHall()
    fun returnItem()
}
