package uk.co.khaleelfreeman.service

sealed class RefreshState {
    object Complete : RefreshState()
    object Fetching : RefreshState()
}