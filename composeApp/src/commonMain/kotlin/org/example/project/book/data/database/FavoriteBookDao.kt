package org.example.project.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun insertBook(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("DELETE FROM BookEntity WHERE id = :bookId")
    suspend fun deleteBook(bookId: String)

    @Query("SELECT * FROM BookEntity WHERE id = :bookId")
    suspend fun getFavoriteBook(bookId: String): BookEntity?

}