package com.example.oop2.libra

inline fun <reified T> List<Any>.filterByType(): List<T> {
    return this.filterIsInstance<T>()
}
