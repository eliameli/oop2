package com.example.oop2.domain.model

import com.example.oop2.R
import com.example.oop2.common.DiskType

class Disk(
    id: Int,
    isAvailable: Boolean,
    name: String,
    val diskType: DiskType,
    override val iconResId: Int = R.drawable.ic_disk
) : LibraryItem(id, isAvailable, name) {



    override val type: String = "Disk"

    override fun getBriefInfo(): String {
        return "Disk: $name, Type: $diskType"
    }

}


//    override fun getBriefInfo(): String =
//        "$diskType $name — ${if (isAvailable) "Доступен" else "Нет"}"
//    override fun getDetailedInfo(): String =
//        "$diskType '$name' (ID: $id)"
//    override fun takeHome() {
//        if (isAvailable) {
//            isAvailable = false
//            println("$diskType $name взяли домой.")
//        } else {
//            println("$diskType $name недоступен для взятия домой.")
//        }
//    }
//
//    override fun readInHall() {
//        println("Нельзя использовать диск $name в читальном зале.")
//    }
//
//    override fun returnItem() {
//        if (!isAvailable) {
//            isAvailable = true
//            println("$diskType $name возвращён.")
//        } else {
//            println("$diskType $name уже доступен.")
//        }
//    }

