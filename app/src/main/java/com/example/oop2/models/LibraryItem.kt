package com.example.oop2.models

sealed class LibraryItem(open val id: Int, var isAvailable: Boolean, open val name: String) {
    abstract fun getIconResId(): Int
    abstract fun getBriefInfo(): String
    abstract fun getDetailedInfo(): String
}


