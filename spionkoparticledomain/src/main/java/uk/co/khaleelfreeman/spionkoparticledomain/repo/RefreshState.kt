package uk.co.khaleelfreeman.spionkoparticledomain.repo

sealed class RefreshState {
    object Complete : RefreshState()
    object Fetching : RefreshState()
}