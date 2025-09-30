package org.example.project.book.presentation.bookList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.book.domain.Book
import org.example.project.book.presentation.bookList.components.BookList
import org.example.project.book.presentation.bookList.components.BookSearchBar
import org.example.project.core.presentation.DarkBlue
import org.example.project.core.presentation.DesertWhite
import org.example.project.core.presentation.SandYellow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.onBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState {
        2
    }
    val searchResultsLazyListState = rememberLazyListState()
    val favoriteResultsLazyListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsLazyListState.animateScrollToItem(0)
    }
//    LaunchedEffect(state.selectedTabIndex){
//        pagerState.animateScrollToPage(state.selectedTabIndex)
//    }
    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.onTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            modifier = Modifier.widthIn(max = 400.dp)
                .fillMaxWidth().padding(16.dp),
            searchQuery = state.searchQuery,
            onSearchQueryChange = { onAction(BookListAction.onSearchQueryChange(it)) },
            onImeAction = { keyboardController?.hide() })
        Surface(
            modifier = Modifier.weight(1f).fillMaxWidth(), color = DesertWhite,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier.padding(vertical = 12.dp).widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    contentColor = SandYellow,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.onTabSelected(0))

                        },
                        modifier = Modifier.weight(1f).clickable{
                            onAction(BookListAction.onTabSelected(0))

                        },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        text = {
                            Text(
                                "Search Results",
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    )
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.onTabSelected(1))
                        },
                        modifier = Modifier.weight(1f).clickable{
                            onAction(BookListAction.onTabSelected(0))

                        },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        text = {
                            Text(
                                text = "Favorites",
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        })

                }

            }
            Spacer(modifier = Modifier.height(140.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) { pageIndex ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when (pageIndex) {
                        0 -> {
                            if (state.isLoading)
                                CircularProgressIndicator()
                            else {
                                when {
                                    state.errorMessage != null -> {
                                        Text(
                                            text = state.errorMessage.asString(),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                    state.searchResults.isEmpty() -> {
                                        Text(
                                            text = "Ooops, there aren't any books matching your search",
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                    else -> {
                                        BookList(
                                            modifier = Modifier.fillMaxSize(),
                                            books = state.searchResults,
                                            onBookClick = { onAction(BookListAction.onBookClick(it)) },
                                            scrollState = searchResultsLazyListState
                                        )

                                    }
                                }
                            }
                        }

                        1 -> {
                            if (state.favoriteBooks.isEmpty()) {
                                Text(
                                    text = "You haven't added any favorites yet",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            } else {
                                BookList(
                                    modifier = Modifier.fillMaxSize(),
                                    books = state.favoriteBooks,
                                    onBookClick = { onAction(BookListAction.onBookClick(it)) },
                                    scrollState = favoriteResultsLazyListState
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    Box(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        BookSearchBar(searchQuery = "", onSearchQueryChange = {}, onImeAction = {})
    }
}