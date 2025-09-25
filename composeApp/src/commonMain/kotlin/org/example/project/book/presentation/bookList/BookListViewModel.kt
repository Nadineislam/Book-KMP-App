package org.example.project.book.presentation.bookList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.onSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }

            }

            is BookListAction.onBookClick -> {

            }
            is BookListAction.onTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}