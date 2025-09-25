package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.example.project.book.presentation.bookList.BookListScreenRoot
import org.example.project.book.presentation.bookList.BookListViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        BookListScreenRoot (viewModel =remember { BookListViewModel() }, onBookClick = {})
    }
}