package com.example.oop2.data.repository

import com.example.oop2.data.local.LibraryDatabase
import com.example.oop2.domain.model.*
import com.example.oop2.common.DiskType
import com.example.oop2.data.local.LibraryItemEntity
import com.example.oop2.data.mapper.mapEntitiesToModels
import com.example.oop2.domain.repositoty.LibraryRepository


class LibraryRepositoryImpl(private val db: LibraryDatabase) : LibraryRepository {

    override suspend fun getItems(): List<LibraryItem> {
        val entities = db.libraryDao().getAllItems()
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

    override suspend fun addItem(item: LibraryItem) {
        val entity = when (item) {
            is Book -> LibraryItemEntity(
                id = item.id,
                name = item.name,
                type = "Book",
                author = item.author,
                pages = item.pages,
                diskType = null,
                issueNumber = null,
                month = null
            )
            is Disk -> LibraryItemEntity(
                id = item.id,
                name = item.name,
                type = "Disk",
                author = null,
                pages = null,
                diskType = item.diskType.name,
                issueNumber = null,
                month = null
            )
            is Newspaper -> LibraryItemEntity(
                id = item.id,
                name = item.name,
                type = "Newspaper",
                author = null,
                pages = null,
                diskType = null,
                issueNumber = item.issueNumber,
                month = item.month
            )
            else -> throw IllegalArgumentException("Unknown item type: ${item::class.simpleName}")
        }
        db.libraryDao().insertItem(entity)
    }

    override suspend fun getItemsSortedByName(limit: Int, offset: Int): List<LibraryItem> {
        val entities = db.libraryDao().getItemsSortedByName()
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


    override suspend fun getItemsSortedByDate(limit: Int, offset: Int): List<LibraryItem> {
        val entities = db.libraryDao().getItemsSortedByDate(limit, offset)
        return mapEntitiesToModels(entities)
    }


}
