package org.example.project.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BookGraph : Route
    @Serializable
    data object BookList : Route
    @Serializable
    data class BookDetail(val bookId: String) : Route
}