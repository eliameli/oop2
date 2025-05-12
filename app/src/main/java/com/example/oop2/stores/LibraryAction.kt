package com.example.oop2.stores

interface LibraryAction {
    fun getBriefInfo(): String  // кратко
    fun getDetailedInfo(): String  // подробно
    // действия
    fun takeHome()
    fun readInHall()
    fun returnItem()
}
