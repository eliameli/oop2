package com.example.oop2.stores

import com.example.oop2.domain.model.Newspaper

class NewspaperStore : Store<Newspaper> {
    override fun sell(): Newspaper {

        return Newspaper(1, true, "Вести", 123, 3)
    }
}
