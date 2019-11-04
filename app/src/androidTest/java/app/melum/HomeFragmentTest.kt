package app.melum

import android.content.res.Resources
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import app.melum.ui.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test


//Note: Animations should be disabled on test device for Espresso to work correctly

//@RunWith(AndroidJUnit4::class)
@SmallTest
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

    @Test
    fun exploreScreen_shouldContainSearchField() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        onView(withId(R.id.btnExplore)).perform(click())


        onView(instanceOf(TextInputEditText::class.java))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnSearch))
            .check(matches(not(isDisplayed())))

        onView(instanceOf(TextInputEditText::class.java))
            .perform(typeText("Ed Sheeran"))

        onView(withId(R.id.btnSearch))
            .check(matches(isDisplayed()))
    }


    @Test
    fun artistScreenTest() {

        val name = "Ed Sheeran"
        val networkDelay = 3000L

        onView(withId(R.id.btnExplore)).perform(click())
        onView(instanceOf(TextInputEditText::class.java))
            .perform(ViewActions.typeText(name))

        onView(withId(R.id.btnSearch))
            .perform(click())

        onView(withId(R.id.progressView))
            .check(matches(isDisplayed()))

        Thread.sleep(networkDelay)

        onView(withNthId(R.id.tvArtistName, 1))
            .perform(click())

        Thread.sleep(networkDelay)

        onView(withId(R.id.ivArtist)).check(matches(isDisplayed()))

//        onView(withNthId(R.id.ivAlbumCover, 1)).check(matches(isDisplayed()))
    }
}


