package com.example.oop2.models
import com.example.oop2.LibraryAction
import com.example.oop2.models.DiskType
import com.example.oop2.models.LibraryItem

class Disk(
    id: Int,
    isAvailable: Boolean,
    name: String,
    val diskType: DiskType
) : LibraryItem(id, isAvailable, name), LibraryAction {
    override val iconResId: Int = R.drawable.ic_disk
    override fun getBriefInfo(): String = "$diskType $name доступна: ${if (isAvailable) "Да" else "Нет"}"

    override fun getDetailedInfo(): String =
        "$diskType $name с id: $id доступна: ${if (isAvailable) "Да" else "Нет"}"

    override fun takeHome() {
        if (isAvailable) {
            isAvailable = false
            println("$diskType $name взяли домой.")
        } else {
            println("$diskType $name недоступен для взятия домой.")
        }
    }

    override fun readInHall() {
        println("Нельзя использовать диск в читальном зале.")
    }

    override fun returnItem() {
        if (!isAvailable) {
            isAvailable = true
            println("$diskType $name возвращен.")
        } else {
            println("$diskType $name уже доступен.")
        }
    }
}
