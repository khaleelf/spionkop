package uk.co.khaleelfreeman.spion.ui.util

import kotlin.reflect.KProperty

class LaunchRefreshDelegate {
    private var counter: Int = 0
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return if (counter == 0) {
            ++counter
            true
        } else {
            false
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) = Unit
}