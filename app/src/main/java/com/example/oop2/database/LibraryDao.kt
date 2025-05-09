package com.example.oop2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: LibraryItemEntity)

    @Query("SELECT * FROM library_items ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getItemsSortedByName(limit: Int, offset: Int): List<LibraryItemEntity>

    @Query("SELECT * FROM library_items ORDER BY createdAt ASC LIMIT :limit OFFSET :offset")
    suspend fun getItemsSortedByDate(limit: Int, offset: Int): List<LibraryItemEntity>

    @Query("SELECT COUNT(*) FROM library_items")
    suspend fun getTotalCount(): Int
}
