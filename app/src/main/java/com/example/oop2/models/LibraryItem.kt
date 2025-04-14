package com.example.oop2.models

import java.io.Serializable

abstract class LibraryItem(
    val id: Int,
    var isAvailable: Boolean,
    val name: String,

) : Serializable {
    abstract fun getBriefInfo(): String
//    abstract fun getDetailedInfo(): String
    abstract val iconResId: Int
    open val type: String = "LibraryItem"
}
