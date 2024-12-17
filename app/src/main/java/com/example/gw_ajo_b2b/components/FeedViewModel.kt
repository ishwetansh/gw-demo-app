package com.example.gw_ajo_b2b.components

import androidx.compose.material.icons.filled.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gw_ajo_b2b.networking.RetrofitClient
import kotlinx.coroutines.launch

class WatchListViewModel : ViewModel() {
    private val _accounts = MutableLiveData<List<AccountData>>()
    val accounts: LiveData<List<AccountData>> = _accounts

    private val useMockData = true

    init {
        fetchWatchList()
    }

    private fun fetchWatchList() {
        if (useMockData) {
            _accounts.value = getMockData()
        } else {
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.apiService.getWatchList()
                    _accounts.value = response
                } catch (e: Exception) {
                    _accounts.value = emptyList()
                }
            }
        }
    }

    /// Mock data for testing purposes
    private fun getMockData(): List<AccountData> {
        return listOf(
            AccountData(
                name = "Bodea",
                activities = listOf(
                    ActivityData(
                        title = "Raymond Noel opened email",
                        subtitle = "Predicting the future is what data was made for",
                        timestamp = "Just now",
                        icon = androidx.compose.material.icons.Icons.Default.Email
                    ),
                    ActivityData(
                        title = "Detected high product intent",
                        subtitle = "",
                        timestamp = "1 day ago",
                        icon = androidx.compose.material.icons.Icons.Default.Check
                    )
                )
            ),
            AccountData(
                name = "Wayne Enterprise",
                activities = listOf(
                    ActivityData(
                        title = "Buying group status changed to",
                        subtitle = "Engaged",
                        timestamp = "1 day ago",
                        icon = androidx.compose.material.icons.Icons.Default.AccountCircle
                    ),
                    ActivityData(
                        title = "Whitney Wall registered for webinar",
                        subtitle = "Total Economic Impact of Adobe Real-Time Customer Data Platform",
                        timestamp = "3 days ago",
                        icon = androidx.compose.material.icons.Icons.Default.Star
                    )
                )
            ),
            AccountData(
                name = "Globex Corporation",
                activities = listOf(
                    ActivityData(
                        title = "Account interesting moment milestone reached",
                        subtitle = "",
                        timestamp = "5 days ago",
                        icon = androidx.compose.material.icons.Icons.Default.List
                    )
                )
            )
        )
    }
}
