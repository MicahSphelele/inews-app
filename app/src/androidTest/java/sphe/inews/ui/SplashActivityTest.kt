package sphe.inews.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.*
import sphe.inews.R
import sphe.inews.di.AppModule
import sphe.inews.test.BuildConfig
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import javax.inject.Inject
import javax.inject.Named


@UninstallModules(AppModule::class)
@HiltAndroidTest
class SplashActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(SplashActivity::class.java)

    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage

    @JvmField
    @Inject
    @Named(Constants.NAMED_IS_ON_TEST_MODE)
    var isOnTestMode: Boolean = false

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testStringAppVersionIsNotNull() {
        Assert.assertNotNull("Testing app version is not null", appVersion)
        Assert.assertEquals("Testing app version is not null", BuildConfig.VERSION_NAME, appVersion)
    }

    @Test
    fun testAppStorageNotNull() {
        Assert.assertNotNull("Testing app storage not null", appStorage)
    }

    @Test
    fun testAppThemeIsSystemDefault() {
        Assert.assertEquals(
            "Testing app theme is in system default",
            "system_default",
            appStorage.getStringData(Constants.KEY_THEME, Constants.DEFAULT_THEME)
        )
    }

    @Test
    fun testIfAppIsInTestMode() {
        Assert.assertTrue("Test if the app is on test mode", isOnTestMode)
    }

    @Test
    fun testImageIconViewViewIsDisplayed() {
        onView(withId(R.id.imageAppIcon)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextViewAppVersionIsDisplayed() {
        onView(withId(R.id.txt_app_version)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextViewAppNameIsDisplayed() {
        onView(withId(R.id.txt_app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextViewAppName() {
        onView(withId(R.id.txt_app_name)).check(matches(ViewMatchers.withText("iNews")))
    }

    @After
    fun tearDown() {
        //Intents.release()
    }
}