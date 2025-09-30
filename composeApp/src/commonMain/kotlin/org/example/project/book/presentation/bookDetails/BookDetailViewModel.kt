package org.example.project.book.presentation.bookDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.app.Route
import org.example.project.book.domain.repository.BookRepository
import org.example.project.core.domain.onSuccess

class BookDetailViewModel(
     savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().bookId
    private var _state = MutableStateFlow(BookDetailState())
    val state = _state.onStart {
        fetchBookDescription(bookId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnFavoriteClick -> {}
            is BookDetailAction.OnSelectedBookChange -> _state.update { it.copy(book = action.book) }
            else -> Unit
        }
    }

    private fun fetchBookDescription(bookId: String) {
        viewModelScope.launch {
            bookRepository.getBookDetails(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(description = description),
                            isLoading = false
                        )
                    }
                }
        }
    }
}