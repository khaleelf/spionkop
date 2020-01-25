package uk.co.khaleelfreeman.spion.service

sealed class RefreshState {
    object Complete : RefreshState()
    object Fetching : RefreshState()
}