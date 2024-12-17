import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.gw_ajo_b2b.components.AccountDetailScreen
import com.example.gw_ajo_b2b.components.WatchListScreen

sealed class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: @Composable (NavHostController) -> Unit
) {
    data object Dashboard : BottomNavItem(
        "Dashboard",
        Icons.Default.Speed,
        { DashboardScreen() }
    )

    data object Feed : BottomNavItem(
        "Feed",
        Icons.Default.Apartment,
        { navController -> WatchListScreen(navController = navController) }
    )

    data object Search : BottomNavItem(
        "Search",
        Icons.Default.Search,
        { SearchScreen() }
    )

    data object BuyingGroups : BottomNavItem(
        "Buying Groups",
        Icons.Default.Groups,
        { AccountDetailScreen() }
    )

    data object Content : BottomNavItem(
        "Content",
        Icons.Default.Folder,
        { ContentScreen() }
    )
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val tabs = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Feed,
        BottomNavItem.Search,
        BottomNavItem.BuyingGroups,
        BottomNavItem.Content
    )

    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color.Gray
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.label,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (selectedTabIndex == index) Color(0xFF2962FF) else Color.Gray
                                )
                            }
                        },
                        label = {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tab.label,
                                    color = if (selectedTabIndex == index) Color(0xFF2962FF) else Color.Gray,
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.Center) {
            tabs[selectedTabIndex].screen(navController)
        }
    }
}


@Composable
fun DashboardScreen() {
    Text("Dashboard Screen", style = MaterialTheme.typography.displaySmall)
}

@Composable
fun FeedScreen(navController: NavHostController) {
    Text("Feed Screen", style = MaterialTheme.typography.displaySmall)
}

@Composable
fun SearchScreen() {
    Text("Search Screen", style = MaterialTheme.typography.displaySmall)
}

@Composable
fun BuyingGroupsScreen() {
    Text("Buying Groups Screen", style = MaterialTheme.typography.displaySmall)
}

@Composable
fun ContentScreen() {
    Text("Content Screen", style = MaterialTheme.typography.displaySmall)
}
