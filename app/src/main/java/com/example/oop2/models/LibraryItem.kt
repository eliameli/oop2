package com.example.oop2.models


import com.example.oop2.R

abstract class LibraryItem(
    val id: Int,
    var isAvailable: Boolean,
    val name: String
) {
    abstract val iconResId: Int
    abstract fun getBriefInfo(): String
    abstract fun getDetailedInfo(): String
}