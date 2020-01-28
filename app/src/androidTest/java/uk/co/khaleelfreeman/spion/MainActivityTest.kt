package uk.co.khaleelfreeman.spion

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import co.uk.khaleelfreeman.service.HttpClient
import uk.co.khaleelfreeman.spion.ui.MainActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest : KoinTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        loadKoinModules(testModule)
    }

    @Test
    fun toolbarShouldDisplayAppTitle() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.toolbar_text_view)).check(matches(withText("Spionkop")))
    }
}

val testModule = module {
    single<HttpClient>(override = true){ TestHttpClient() }
}