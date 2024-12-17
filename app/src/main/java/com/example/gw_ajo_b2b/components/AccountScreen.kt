package com.example.gw_ajo_b2b.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountDetailScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        GradientHeader()

        AccountSummaryHeader()

        SegmentedControl(
            tabs = listOf("Overview", "Intent data", "Buying groups"),
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        when (selectedTab) {
            0 -> OverviewContent()
            1 -> PlaceholderScreen("Intent data")
            2 -> PlaceholderScreen("Buying groups")
        }
    }
}

@Composable
fun GradientHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFF5F6D), Color(0xFF6BA9FF))
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .align(Alignment.TopStart)
                .clickable { /* Navigate Back */ }
        )
        CircleAvatar(
            letter = 'B',
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 40.dp)
        )
    }
}

@Composable
fun AccountSummaryHeader() {
    Column(modifier = Modifier.padding(top = 48.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "Bodea",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            AccountInfoItem(label = "Industry", value = "Technology")
            AccountInfoItem(label = "People", value = "25")
            AccountInfoItem(label = "Opportunities", value = "4")
            AccountInfoItem(label = "Buying groups", value = "8")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AssistButton("AI assistant", Color.Gray)
            AssistButton("Contact recommendations", Color(0xFF2962FF))
        }
    }
}

@Composable
fun AccountInfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
        Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun AssistButton(text: String, backgroundColor: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(backgroundColor, MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SegmentedControl(tabs: List<String>, selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, title ->
            Text(
                text = title,
                color = if (selectedTab == index) Color.Black else Color.Gray,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { onTabSelected(index) }
            )
        }
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}

@Composable
fun OverviewContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bodea has been recently engaged with content related to Analytics, Marketo, and Journey Optimizer B2B Edition. The most engaged buying group is Analytics which is now marketing qualified.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "🏆 Top engaged buying groups are Analytics and Marketo\n" +
                    "✅ Top engaged people are John Smith and Jane Appleseed\n" +
                    "ℹ️ No alerts! (high five)",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
        )
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CircleAvatar(letter: Char, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Color(0xFFFFC107), shape = CircleShape)
    ) {
        Text(
            text = letter.toString(),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
