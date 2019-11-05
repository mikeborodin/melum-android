package app.melum

import android.content.res.Resources
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import app.melum.common.RecyclerBindingAdapter
import app.melum.ui.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTests {

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
    fun homeScreen_shouldOpenDetails() {

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

    @Test
    fun exploreScreen_shouldContainSearchField() {

        onView(withId(R.id.btnExplore)).perform(click())


        onView(instanceOf(TextInputEditText::class.java))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnSearch))
            .check(matches(not(isDisplayed())))

        onView(instanceOf(TextInputEditText::class.java))
            .perform(typeText(artistName))

        onView(withId(R.id.btnSearch))
            .check(matches(isDisplayed()))
    }


    @Test
    fun artistScreen_shouldContainTopAlbums() {


        onView(withId(R.id.btnExplore)).perform(click())

        onView(instanceOf(TextInputEditText::class.java)).perform(typeText(artistName))

        onView(withId(R.id.btnSearch)).perform(click())

        onView(withId(R.id.progressView)).check(matches(isDisplayed()))

        Thread.sleep(networkDelay)

        onView(withId(R.id.rvArtists)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerBindingAdapter.BindingHolder>(
                2,
                click()
            )
        )

        Thread.sleep(networkDelay)

        onView(withId(R.id.ivArtist)).check(matches(isDisplayed()))

    }


    @Test
    fun albumScreen_shouldHaveSaveOption() {

        onView(withId(R.id.btnExplore)).perform(click())

        onView(instanceOf(TextInputEditText::class.java)).perform(typeText(artistName))

        onView(withId(R.id.btnSearch)).perform(click())

        Thread.sleep(networkDelay)

        onView(withId(R.id.rvArtists)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerBindingAdapter.BindingHolder>(
                0,
                click()
            )
        )

        Thread.sleep(networkDelay)

        onView(withId(R.id.rvAlbums)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerBindingAdapter.BindingHolder>(
                1,
                click()
            )
        )

        onView(withId(R.id.ivAlbumCover)).check(matches(isDisplayed()))

        onView(withId(R.id.btnSave)).check(matches(isDisplayed()))

        onView(withId(R.id.btnSave)).perform(click())
    }

    companion object {
        const val artistName = "Ed Sheeran"
        const val networkDelay = 3000L
    }
}


