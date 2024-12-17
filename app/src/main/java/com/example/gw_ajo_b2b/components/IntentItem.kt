package com.example.app.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class IntentCategory(
    val title: String,
    val subtitle: String,
    val items: List<IntentItem>
)

data class IntentItem(
    val title: String,
    val intentLevel: String
)

@Composable
fun IntentCategoryItem(category: IntentCategory) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF3E5F5))
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tag,
                        contentDescription = "Category Icon",
                        tint = Color(0xFF9C27B0), // Purple
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = category.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = Color(0xFF388E3C)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "High intent",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color(0xFF388E3C),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.6f), thickness = 1.dp)

            val displayedItems = if (isExpanded) category.items else category.items.take(2)
            displayedItems.forEach { item ->
                IntentItemRow(item = item)
            }

            if (category.items.size > 2) {
                Spacer(modifier = Modifier.height(8.dp))
                AssistChip(
                    onClick = { isExpanded = !isExpanded },
                label = {
                    Text(text = "View More")
                },
                enabled = true,
                leadingIcon =  {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = "Expand/Collapse",
                        tint = Color.Gray,
                    )
                },
                shape = AssistChipDefaults.shape,
                )
            }
        }
    }
}

@Composable
fun IntentItemRow(item: IntentItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Tag,
                contentDescription = "Intent Item",
                tint = Color(0xFF9C27B0),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }
        Row {
            Text(
                text = item.intentLevel,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "View keywords",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF2962FF),
                    textDecoration = TextDecoration.Underline,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun IntentListScreen(categories: List<IntentCategory>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF3E5F5))
    ) {
        categories.forEach { category ->
            IntentCategoryItem(category)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntentListPreview() {
    val mockCategories = listOf(
        IntentCategory(
            title = "Personalization at scale",
            subtitle = "Category",
            items = listOf(
                IntentItem("Journey Optimizer", "high intent"),
                IntentItem("Journey Optimizer B2B Edition", "high intent")
            )
        ),
        IntentCategory(
            title = "Content management",
            subtitle = "Category",
            items = listOf(
                IntentItem("Content management system", "high intent"),
                IntentItem("Digital asset management", "high intent"),
                IntentItem("Asset Management Tools", "medium intent")
            )
        ),
        IntentCategory(
            title = "B2B marketing",
            subtitle = "Category",
            items = listOf(
                IntentItem("Account Based Marketing", "high intent"),
                IntentItem("B2B Data Analytics", "high intent")
            )
        )
    )
    IntentListScreen(categories = mockCategories)
}
