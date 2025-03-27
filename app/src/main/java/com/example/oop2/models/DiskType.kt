package com.example.oop2.models
import com.example.oop2.models.LibraryItem

enum class DiskType { CD, DVD }



data class LibraryItem(
    val id: Int,
    val name: String,
    var isAvailable: Boolean,
    val iconResId: Int // id dlya ikonki
)
