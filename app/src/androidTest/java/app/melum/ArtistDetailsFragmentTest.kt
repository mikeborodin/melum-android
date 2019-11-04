package app.melum

import android.content.res.Resources
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import app.melum.ui.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistDetailsFragmentTest {

    val networkDelay = 3000L

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        val name = "Ed Sheeran"

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

    }


    @Test
    fun artistScreen_shouldHaveImage() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources

        onView(instanceOf(ImageView::class.java)).check(matches(isDisplayed()))

    }

    @Test
    fun artistScreen_shouldHaveAlbums() {
        val resources: Resources =
            InstrumentationRegistry.getInstrumentation().targetContext.resources
        onView(withNthId(R.id.ivAlbumCover, 1)).check(matches(isDisplayed()))
    }

}
