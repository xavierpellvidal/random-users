package com.random.users.users.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.users.contract.UserUiState
import com.random.users.users.model.UserLocationUiModel
import com.random.users.users.model.UserNameUiModel
import com.random.users.users.model.UserPictureUiModel
import com.random.users.users.model.UserStreetUiModel
import com.random.users.users.model.UserUiModel

@Composable
internal fun UserCard(
    modifier: Modifier = Modifier,
    user: UserUiState,
    onDeleteUser: (String) -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(user.user.picture.thumbnail)
                        .crossfade(true)
                        .build(),
                contentDescription = "Picture of ${user.user.name.first} ${user.user.name.last}",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "${user.user.name.first} ${user.user.name.last}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = user.user.email,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = user.user.phone,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            when (user.userState) {
                UserUiState.ContentState.Deleting -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                    )
                }
                else -> {
                    IconButton(
                        modifier = Modifier.testTag("${user.user.uuid}-delete"),
                        onClick = { onDeleteUser(user.user.uuid) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete user",
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun UserCardPreview() {
    RandomUsersTheme {
        UserCard(
            user =
                UserUiState(
                    user =
                        UserUiModel(
                            uuid = "550e8400-e29b-41d4-a716-446655440000",
                            name =
                                UserNameUiModel(
                                    first = "María",
                                    last = "García",
                                ),
                            location =
                                UserLocationUiModel(
                                    street =
                                        UserStreetUiModel(
                                            number = 123,
                                            name = "Calle Mayor",
                                        ),
                                    city = "Madrid",
                                    state = "Madrid",
                                ),
                            email = "maria.garcia@example.com",
                            phone = "+34 612 345 678",
                            gender = "female",
                            picture =
                                UserPictureUiModel(
                                    medium = "https://randomuser.me/api/portraits/women/42.jpg",
                                    thumbnail = "https://randomuser.me/api/portraits/thumb/women/42.jpg",
                                ),
                        ),
                    userState = UserUiState.ContentState.Idle,
                ),
            onDeleteUser = {},
        )
    }
}
