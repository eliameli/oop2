package com.example.oop2.data.mapper

import com.example.oop2.data.local.LibraryItemEntity
import com.example.oop2.domain.model.*
import com.example.oop2.common.DiskType

fun mapEntitiesToModels(entities: List<LibraryItemEntity>): List<LibraryItem> {
    return entities.mapNotNull { entity ->
        when (entity.type) {
            "Book" -> Book(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                author = entity.author ?: return@mapNotNull null,
                pages = entity.pages ?: return@mapNotNull null
            )
            "Disk" -> Disk(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                diskType = entity.diskType?.let { DiskType.valueOf(it) } ?: DiskType.CD
            )
            "Newspaper" -> Newspaper(
                id = entity.id,
                isAvailable = true,
                name = entity.name,
                issueNumber = entity.issueNumber ?: return@mapNotNull null,
                month = entity.month ?: return@mapNotNull null
            )
            else -> null
        }
    }
}
