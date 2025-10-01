package org.example.project.book.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.book.data.database.FavoriteBookDao
import org.example.project.book.data.mappers.toBook
import org.example.project.book.data.mappers.toBookEntity
import org.example.project.book.data.network.RemoteBookDataSource
import org.example.project.book.domain.Book
import org.example.project.book.domain.repository.BookRepository
import org.example.project.core.data.Result
import org.example.project.core.domain.DataError
import org.example.project.core.domain.EmptyResult
import org.example.project.core.domain.Result
import org.example.project.core.domain.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val bookDao: FavoriteBookDao
) : BookRepository {
    override suspend fun searchBooks(
        query: String
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto ->
            dto.results.map { it.toBook() }
        }
    }

    override suspend fun getBookDetails(bookId: String): Result<String?, DataError> {
        val localResult = bookDao.getFavoriteBook(bookId)
        return if (localResult == null) {
            remoteBookDataSource.getBookDetails(bookId).map {
                it.description
            }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return bookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.map {
                it.toBook()
            }
        }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return bookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.any { it.id == id }
        }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            bookDao.insertBook(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        return bookDao.deleteBook(bookId = id)
    }
}