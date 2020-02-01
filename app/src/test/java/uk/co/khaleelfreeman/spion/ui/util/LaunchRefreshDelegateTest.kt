package uk.co.khaleelfreeman.spion.ui.util

import org.junit.Assert.assertFalse
import org.junit.Test

class LaunchRefreshDelegateTest {

    /**
     * LaunchRefreshDelegate() is a property delegate. So it's set inside other objects/classes
     */
    object TestObject  {
        val delegate : Boolean by LaunchRefreshDelegate()
    }


    @Test
    fun onFirstReadValueShouldBeTrue() {
        assert(TestObject.delegate)
    }

    @Test
    fun onSubsequentReadsValueShouldBeFalse() {
        assertFalse(TestObject.delegate)
        assertFalse(TestObject.delegate)
        assertFalse(TestObject.delegate)
    }
}