package com.example.oop2.stores

import com.example.oop2.Store
import com.example.oop2.models.Newspaper

class NewspaperStore : Store<Newspaper> {
    override fun sell(): Newspaper {

        return Newspaper(1, true, "Вести", 123, 3)
    }
}
