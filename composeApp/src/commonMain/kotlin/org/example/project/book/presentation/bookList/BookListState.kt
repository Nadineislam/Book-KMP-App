package org.example.project.book.presentation.bookList

import org.example.project.book.domain.Book
import org.example.project.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,
    val selectedTabIndex: Int = 0
)