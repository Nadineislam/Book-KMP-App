package org.example.project.book.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.book.domain.Book
import org.example.project.book.presentation.bookList.BookListScreen
import org.example.project.book.presentation.bookList.BookListState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BookList(
    modifier: Modifier,
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier.padding(top = 88.dp), state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = books, key = { it.id }) { book ->
            BookListItem(
                modifier = modifier.widthIn(max = 700.dp).fillMaxWidth()
                    .padding(horizontal = 16.dp), book = book, onClick = { onBookClick(book) })
        }
    }
}
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

@Preview
@Composable
fun BookListPreview() {
    BookListScreen(state = BookListState(searchResults = books), onAction = {})
}