package co.uk.khaleelfreeman.service

sealed class RefreshState {
    object Complete : RefreshState()
    object Fetching : RefreshState()
}