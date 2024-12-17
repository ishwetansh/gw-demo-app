package com.example.app.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AccountDetailScreen(accountName: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader(navController, accountName)
        AccountHeaderSection(accountName)
        AccountActionsRow()
        PageSegmentTabs()
        SummarySection()
        EngagementSection()
    }
}

@Composable
fun GradientHeader(navController: NavHostController, accountName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFFB833FF), Color(0xFF63B4FF))
                )
            )
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            CircleAvatar(letter = accountName.firstOrNull()?.uppercaseChar())
        }
    }
}

@Composable
fun AccountHeaderSection(accountName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = accountName,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AccountDetailItem(label = "Industry", value = "Technology")
            AccountDetailItem(label = "People", value = "25")
            AccountDetailItem(label = "Opportunities", value = "4")
            AccountDetailItem(label = "Buying groups", value = "8")
        }
    }
}

@Composable
fun AccountDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
        Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun AccountActionsRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ActionButton(
            text = "AI assistant",
            icon = Icons.Default.Face,
            backgroundColor = Color(0xFFE0E0E0)
        )
        ActionButton(
            text = "Contact recommendations",
            icon = Icons.Default.Email,
            backgroundColor = Color(0xFF2962FF),
            contentColor = Color.White
        )
    }
}

@Composable
fun ActionButton(text: String, icon: ImageVector, backgroundColor: Color, contentColor: Color = Color.Black) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = contentColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = contentColor))
    }
}

@Composable
fun PageSegmentTabs() {
    val tabs = listOf("Overview", "Intent data", "Buying groups")
    var selectedTab by remember { mutableStateOf(0) }

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun SummarySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bodea has been recently engaged with content related to Analytics, Marketo, and Journey Optimizer B2B Edition.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFFFC107))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Top engaged buying groups are Analytics and Marketo")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Top engaged people are John Smith and Jane Appleseed")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("No alerts! (high five)")
        }
    }
}

@Composable
fun EngagementSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Engagement",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text("Engagement Graph Placeholder", color = Color.Gray)
        }
    }
}

@Composable
fun CircleAvatar(letter: Char?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .background(color = Color(0xFFFFC107), shape = CircleShape)
    ) {
        Text(
            text = letter?.toString() ?: "?",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
