package org.example.project.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.example.project.book.presentation.bookList.BookListScreenRoot
import org.example.project.book.presentation.bookList.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Route.BookGraph) {
            navigation<Route.BookGraph>(startDestination = Route.BookList) {
                composable<Route.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = {book->
                            navController.navigate(Route.BookDetail(book.id))
                        }
                    )
                }
                composable<Route.BookDetail> {
                    val bookId = navController.currentBackStackEntry?.arguments?.getString("bookId")
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Book Detail $bookId", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}