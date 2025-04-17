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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.random.user.presentation.ui.theme.RandomUsersTheme

@Composable
fun UserSearchView(
    modifier: Modifier = Modifier,
    search: String = "",
    onValueChange: (String) -> Unit,
) {
    val searchState = rememberSaveable { mutableStateOf(search) }

    TextField(
        modifier = modifier.fillMaxWidth(),
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
fun CustomSearchViewPreview() {
    RandomUsersTheme {
        UserSearchView(
            search = "Search",
            onValueChange = {},
        )
    }
}
