package com.example.oop2.stores

import com.example.oop2.domain.model.Disk
import com.example.oop2.common.DiskType

class DiskStore : Store<Disk> {
    override fun sell(): Disk {

        return Disk(1, true, "Мстители", DiskType.CD)
    }
}
