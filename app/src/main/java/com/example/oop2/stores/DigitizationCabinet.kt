package com.example.oop2.stores


import com.example.oop2.models.*

class DigitizationCabinet<T : LibraryItem, R : Disk> {
    fun digitize(item: T): R {
        return Disk(
            id = item.id,
            isAvailable = true,
            name = "Оцифрованный: ${item.name}",
            diskType = DiskType.CD
        ) as R
    }
}