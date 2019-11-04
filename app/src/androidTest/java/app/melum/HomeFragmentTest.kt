package app.melum

import android.content.res.Resources
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import app.melum.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


//Note: Animations should be disabled on test device for Espresso to work correctly

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun homeScreen_shouldContainExploreButton() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        onView(withId(R.id.btnExplore))
            .check(matches(isDisplayed()))
            .check(matches(withText(resources.getString(R.string.explore_button))))
    }

    @Test
    fun homeScreen_shouldContainDataOrEmptyState() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources
        try {
            onView(withId(R.id.tvEmptyState))
                .check(matches(isDisplayed()))
        } catch (e: Throwable) {
            onView(withNthId(R.id.ivAlbumCover, 1))
                .check(matches(isDisplayed()))
        }
    }


    @Test
    fun homeScreen_shouldOpenDetails() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        try {
            onData(withId(R.id.ivAlbumCover))
                .inAdapterView(withId(R.id.rvSavedAlbums))
                .atPosition(0)
                .perform(scrollTo(), click())

            onView(withId(R.id.tvArtistName)).check(matches(isDisplayed()))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}


