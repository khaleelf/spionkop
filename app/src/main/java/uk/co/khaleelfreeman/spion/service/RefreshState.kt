package uk.co.khaleelfreeman.spion.service

import co.uk.khaleelfreeman.service.RefreshState

sealed class RefreshState {
    object Complete : RefreshState()
    object Fetching : RefreshState()
}