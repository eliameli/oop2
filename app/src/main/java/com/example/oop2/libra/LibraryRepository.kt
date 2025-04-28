package com.example.oop2.libra

import android.content.Context
import androidx.room.Room
import com.example.oop2.database.LibraryDatabase
import com.example.oop2.database.LibraryItemEntity
import com.example.oop2.models.*

object LibraryRepository {
    private var db: LibraryDatabase? = null
    fun initDatabase(context: Context) {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                LibraryDatabase::class.java,
                "library.db"
            ).build()
        }
    }
    private fun getDatabase(): LibraryDatabase {
        return db ?: throw IllegalStateException("Database is not initialized.")
    }

    suspend fun insert(item: LibraryItemEntity) {
        getDatabase().libraryDao().insertItem(item)
    }

    suspend fun getItemsSortedByName(limit: Int, offset: Int) =
        getDatabase().libraryDao().getItemsSortedByName(limit, offset)

    suspend fun getItemsSortedByDate(limit: Int, offset: Int) =
        getDatabase().libraryDao().getItemsSortedByDate(limit, offset)

    suspend fun getTotalCount() = getDatabase().libraryDao().getTotalCount()

    suspend fun initializeIfEmpty() {
        val count = getTotalCount()
        if (count == 0) {
            val items = getInitialData()
            for (model in items) {
                val entity = when (model) {
                    is Book -> LibraryItemEntity(
                        name = model.name,
                        type = "Book",
                        author = model.author,
                        pages = model.pages,
                        diskType = null,
                        issueNumber = null,
                        month = null
                    )
                    is Disk -> LibraryItemEntity(
                        name = model.name,
                        type = "Disk",
                        author = null,
                        pages = null,
                        diskType = model.diskType.name,
                        issueNumber = null,
                        month = null
                    )
                    is Newspaper -> LibraryItemEntity(
                        name = model.name,
                        type = "Newspaper",
                        author = null,
                        pages = null,
                        diskType = null,
                        issueNumber = model.issueNumber,
                        month = model.month
                    )
                    else -> throw IllegalArgumentException("Unknown model type: ${model::class.simpleName}")
                }
                insert(entity)
            }
        }
    }
    private fun getInitialData(): List<LibraryItem> {
        val books = listOf(
            Book(1, true, "Маугли", "Джозеф Киплинг", 202),
            Book(11, true, "Звездные войны, Часть 1", "Джордж Лукас", 401),
            Book(12, true, "Звездные войны, Часть 2", "Джордж Лукас", 412),
            Book(13, true, "Звездные войны, Часть 3", "Джордж Лукас", 441),
            Book(14, true, "Звездные войны, Часть 4", "Джордж Лукас", 423),
            Book(15, true, "Звездные войны, Часть 5", "Джордж Лукас", 363),
            Book(16, true, "Звездные войны, Часть 6", "Джордж Лукас", 621),
            Book(17, true, "Робинзон Крузо", "Даниель Дефо", 666)
        )
        val disks = listOf(
            Disk(3, true, "Дэдпул и Росомаха", DiskType.CD),
            Disk(31, true, "Один Дома", DiskType.DVD)
        )
        val newspapers = listOf(
            Newspaper(2, true, "Сельская жизнь", 794, 3),
            Newspaper(21, true, "Семья", 23, 12)
        )
        return books + disks + newspapers
    }
}
