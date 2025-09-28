package org.example.project.book.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.search_hint
import org.example.project.core.presentation.DarkBlue
import org.example.project.core.presentation.DesertWhite
import org.example.project.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            modifier = modifier
                .background(shape = RoundedCornerShape(100), color = DesertWhite)
                .minimumInteractiveComponentSize(),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(Res.string.search_hint),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = searchQuery.isNotBlank()) {
                    IconButton(onClick = {
                        onSearchQueryChange("")
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(Res.string.search_hint),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            shape = RoundedCornerShape(100),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue,
//                focusedContainerColor = SandYellow
            ),
            placeholder = {
                Text(text = stringResource(Res.string.search_hint))
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onSearch = { onImeAction }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
        )
    }
}