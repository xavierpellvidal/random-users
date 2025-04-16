package com.random.users.users.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.random.user.presentation.ui.theme.RandomUsersTheme

@Composable
fun UserSearchView(
    modifier: Modifier = Modifier,
    search: String = "",
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = search,
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
        placeholder = { Text(text = "Search") },
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
