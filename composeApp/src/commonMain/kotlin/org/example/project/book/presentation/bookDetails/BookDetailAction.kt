package org.example.project.book.presentation.bookDetails

import org.example.project.book.domain.Book

sealed interface BookDetailAction {
    data object OnFavoriteClick : BookDetailAction
    data object OnBackClick : BookDetailAction
    data class OnSelectedBookChange(val book: Book?) : BookDetailAction
}