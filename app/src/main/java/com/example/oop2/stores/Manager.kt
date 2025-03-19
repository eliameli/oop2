package com.example.oop2.stores

import com.example.oop2.Store
import com.example.oop2.models.LibraryItem

class Manager {
    fun <T : LibraryItem> buy(store: Store<T>): T {

        return store.sell()
    }
}
