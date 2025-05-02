package com.random.users.users.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.random.user.presentation.ui.theme.RandomUsersTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
internal fun UserSearchView(
    modifier: Modifier = Modifier,
    search: String = "",
    onValueChange: (String) -> Unit,
) {
    val searchState = rememberSaveable { mutableStateOf(search) }

    LaunchedEffect(Unit) {
        snapshotFlow { searchState.value }
            .distinctUntilChanged()
            .debounce(200)
            .collectLatest { onValueChange(it) }
    }

    TextField(
        modifier = modifier.fillMaxWidth().testTag("searchField"),
        value = searchState.value,
        onValueChange = { newText ->
            searchState.value = newText
            onValueChange(newText)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (searchState.value.isNotEmpty()) {
                Icon(
                    modifier =
                        Modifier.clickable {
                            searchState.value = ""
                            onValueChange("")
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                )
            }
        },
        placeholder = {
            Text(text = "Search")
        },
    )
}

@PreviewLightDark
@Composable
private fun CustomSearchViewPreview() {
    RandomUsersTheme {
        UserSearchView(
            search = "Search",
            onValueChange = {},
        )
    }
}
