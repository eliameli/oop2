package com.example.oop2.models
import com.example.oop2.LibraryAction
import com.example.oop2.models.DiskType
import com.example.oop2.models.LibraryItem
import com.example.oop2.R

class Disk(
    id: Int,
    isAvailable: Boolean,
    name: String,
    val diskType: DiskType
) : LibraryItem(id, isAvailable, name), LibraryAction {

    override val iconResId: Int = R.drawable.ic_disk

    override fun getBriefInfo(): String =
        "$diskType $name — ${if (isAvailable) "Доступен" else "Нет"}"

    override fun getDetailedInfo(): String =
        "$diskType '$name' (ID: $id)"

    override fun takeHome() {
        if (isAvailable) {
            isAvailable = false
            println("$diskType $name взяли домой.")
        } else {
            println("$diskType $name недоступен для взятия домой.")
        }
    }

    override fun readInHall() {
        println("Нельзя использовать диск $name в читальном зале.")
    }

    override fun returnItem() {
        if (!isAvailable) {
            isAvailable = true
            println("$diskType $name возвращён.")
        } else {
            println("$diskType $name уже доступен.")
        }
    }
}
