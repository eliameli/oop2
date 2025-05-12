package com.example.oop2.stores

import com.example.oop2.domain.model.LibraryItem

class Manager {
    fun <T : LibraryItem> buy(store: Store<T>): T {

        return store.sell()
    }
}
