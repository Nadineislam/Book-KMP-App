package org.example.project.book.presentation.bookDetails

import org.example.project.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book?=null
)
