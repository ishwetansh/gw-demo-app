package com.example.gw_ajo_b2b.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun WatchListScreen(navController: NavHostController, viewModel: WatchListViewModel = WatchListViewModel()) {
    val accounts by viewModel.accounts.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Watch list",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        if (accounts.isEmpty()) {
            EmptyStateView()
        } else {
            AccountListView(accounts = accounts, navController = navController)
        }
    }
}

@Composable
fun EmptyStateView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No accounts to display",
            style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray)
        )
    }
}

@Composable
fun AccountListView(accounts: List<AccountData>, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFACCFFD))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {
            items(accounts) { account ->
                AccountCard(
                    accountData = account,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .clickable {
                            navController.navigate("account_screen/${account.name}")
                        }
                )
            }
        }
    }
}

@Composable
fun AccountCard(accountData: AccountData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircleAvatar(letter = accountData.name.firstOrNull()?.uppercaseChar())
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = accountData.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E88E5)
                    )
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "High product intent",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF388E3C))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier.padding(start = 8.dp, end = 4.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        accountData.activities.forEach { activity ->
            ActivityItem(activityData = activity)
        }
    }
}

@Composable
fun ActivityItem(activityData: ActivityData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = activityData.icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            tint = Color.Gray
        )
        Column {
            Text(
                text = activityData.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            if (activityData.subtitle.isNotEmpty()) {
                Text(
                    text = activityData.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF1E88E5))
                )
            }
            Text(
                text = activityData.timestamp,
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
            )
        }
    }
}

@Composable
fun CircleAvatar(letter: Char?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(color = Color(0xFFFFC107), shape = CircleShape)
    ) {
        Text(
            text = letter?.toString() ?: "?",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

data class AccountData(
    val name: String,
    val activities: List<ActivityData>
)

data class ActivityData(
    val title: String,
    val subtitle: String,
    val timestamp: String,
    val icon: ImageVector
)
