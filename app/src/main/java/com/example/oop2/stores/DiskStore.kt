package com.example.oop2.stores

import com.example.oop2.Store
import com.example.oop2.models.*

class DiskStore : Store<Disk> {
    override fun sell(): Disk {

        return Disk(1, true, "Мстители", DiskType.CD)
    }
}
