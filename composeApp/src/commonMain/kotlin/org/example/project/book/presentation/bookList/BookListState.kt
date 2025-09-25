package org.example.project.book.presentation.bookList

import org.example.project.book.domain.Book
import org.example.project.core.presentation.UiText

data class BookListState(
    val searchQuery: String="kotlin",
    val searchResults: List<Book> = books,
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val selectedTabIndex: Int = 0
)
val books=(1..100).map {
    Book(id = it.toString(),
        title = "Book $it",
        imageUrl = "https://via.placeholder.com/150",
        authors = listOf("Author $it"),
        averageRating = it.toDouble(),
        description = "Description $it",
        ratingCount = it,
        firstPublishYear = it.toString()
        ,languages = listOf("en"),
        numPages = it,
        numEditions = it
    )

}