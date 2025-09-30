package org.example.project.book.domain.repository

import org.example.project.book.domain.Book
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDetails(bookId: String): Result<String?, DataError.Remote>
}