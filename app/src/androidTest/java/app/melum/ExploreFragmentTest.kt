package app.melum

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import app.melum.ui.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ExploreFragmentTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.btnExplore)).perform(click())
    }

    @Test
    fun exploreScreen_shouldContainSearchField() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        onView(instanceOf(TextInputEditText::class.java))
            .check(matches(isDisplayed()))
    }


    @Test
    fun exploreScreen_searchButtonAppearsOnType() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        onView(withId(R.id.btnSearch))
            .check(matches(not(isDisplayed())))

        onView(instanceOf(TextInputEditText::class.java))
            .perform(typeText("Ed Sheeran"))

        onView(withId(R.id.btnSearch))
            .check(matches(isDisplayed()))

    }

    @Test
    fun exploreScreen_searchShouldDisplayData() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        val name = "Ed Sheeran"
        onView(instanceOf(TextInputEditText::class.java))
            .perform(typeText(name))

        onView(withId(R.id.btnSearch))
            .perform(click())

        onView(withId(R.id.progressView)).check(matches(isDisplayed()))

        Thread.sleep(3000)

        onView(withNthId(R.id.tvArtistName, 1))
            .check(matches(withText(name)))

    }

}
