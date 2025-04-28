package com.example.oop2.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "library_items")
data class LibraryItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val type: String,
    val author: String?,
    val pages: Int?,
    val diskType: String?,
    val issueNumber: Int?,
    val month: Int?,
    val createdAt: Long = System.currentTimeMillis()
)