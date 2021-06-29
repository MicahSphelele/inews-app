package sphe.inews.ui.main

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sphe.inews.R
import sphe.inews.di.AppModule
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import javax.inject.Inject
import javax.inject.Named

@UninstallModules(AppModule::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage

    lateinit var testContext: Context

    @Before
    fun setUp() {
        testContext = getInstrumentation().targetContext
        hiltRule.inject()
    }

    @Test
    fun testAppStorageNotNull() {
        Assert.assertNotNull("Testing app storage not null", appStorage)
    }

    @Test
    fun testAboutFragmentScreen() {
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(testContext.getString(R.string.about) )).perform(click())
        onView(withId(R.id.constrainLayout)).perform(click())
    }

    @Test
    fun testAppThemeChangedToDarkMode() {
        //Open Options Menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        //Click Options Menu Item
        onView(withText("UI Theme")).perform(click())
        //Open AlertDialog and click Dark Mode
        onView(withText("Dark Mode")).perform(click())
        //Save UI Mode State
        onView(withText("SAVE")).perform(click())
    }


    @Test
    fun testBottomBarOverIsDisplayed() {
        onView(withId(R.id.appBottomNavigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testIfToolbarIsDisplayed() {
        onView(withId(R.id.appToolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}