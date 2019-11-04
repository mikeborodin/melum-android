package app.melum

//@RunWith(AndroidJUnit4::class)
/*
@SmallTest
class AlbumDetailsFragmentTest {

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
        Thread.sleep(networkDelay)

        */
/*onView(withNthId(R.id.tvArtistName, 1))
            .perform(click())*//*


        Thread.sleep(networkDelay)

onView(withNthId(R.id.tvAlbumTitle, 1))
            .perform(click())


        onData(instanceOf(Album::class.java)).inAdapterView(withId(R.id.rvAlbums))
            .atPosition(1).perform(click())

        Thread.sleep(networkDelay)

    }


    @Test
    fun albumScreen_shouldHaveImageAndTitle() {

        onView(withId(R.id.ivAlbumCover)).check(matches(isDisplayed()))

        */
/*onView(withNthId(R.id.tvSongName, 1))
            .check(matches(isDisplayed()))*//*


    }


}
*/
