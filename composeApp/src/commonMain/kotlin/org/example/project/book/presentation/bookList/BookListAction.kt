package org.example.project.book.presentation.bookList

import org.example.project.book.domain.Book

sealed interface BookListAction {
    data class onSearchQueryChange(val query: String): BookListAction
    data class onBookClick(val book: Book): BookListAction
    data class onTabSelected(val index: Int): BookListAction
}