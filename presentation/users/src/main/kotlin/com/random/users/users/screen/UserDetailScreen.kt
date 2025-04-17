package com.random.users.users.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.users.model.UserLocationUiModel
import com.random.users.users.model.UserNameUiModel
import com.random.users.users.model.UserPictureUiModel
import com.random.users.users.model.UserStreetUiModel
import com.random.users.users.model.UserUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserDetailScreen(
    navController: NavController,
    user: UserUiModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("${user.name.first} ${user.name.last}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        UserDetailContent(
            modifier = Modifier.padding(innerPadding),
            user = user,
        )
    }
}

@Composable
internal fun UserDetailContent(
    modifier: Modifier = Modifier,
    user: UserUiModel,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
    ) {
        ProfileCard(user = user)
        ContactInformationCard(email = user.email, phone = user.phone)
        AddressCard(location = user.location)
    }
}

@Composable
fun ProfileCard(user: UserUiModel) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
        ) {
            AsyncImage(
                model = user.picture.medium,
                contentDescription = "Picture of ${user.name.first}",
                modifier =
                    Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${user.name.first} ${user.name.last}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = user.gender.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
fun ContactInformationCard(
    email: String,
    phone: String,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Contact information",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 12.dp),
                )
                Text(text = email)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 12.dp),
                )
                Text(text = phone)
            }
        }
    }
}

@Composable
fun AddressCard(location: UserLocationUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Address",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 12.dp),
                )
                Column {
                    Text(
                        text = "${location.street.number} ${location.street.name}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "${location.city}, ${location.state}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun UserDetailScreenPreview() {
    RandomUsersTheme {
        UserDetailScreen(
            navController = rememberNavController(),
            user =
                UserUiModel(
                    uuid = "550e8400-e29b-41d4-a716-446655440000",
                    name = UserNameUiModel(first = "John", last = "Doe"),
                    location =
                        UserLocationUiModel(
                            street = UserStreetUiModel(number = 123, name = "Main Street"),
                            city = "Springfield",
                            state = "Illinois",
                        ),
                    email = "john.doe@example.com",
                    phone = "+1 555-123-4567",
                    gender = "male",
                    picture =
                        UserPictureUiModel(
                            medium = "https://randomuser.me/api/portraits/men/1.jpg",
                            thumbnail = "https://randomuser.me/api/portraits/thumb/men/1.jpg",
                        ),
                ),
        )
    }
}
