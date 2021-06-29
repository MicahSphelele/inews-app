package sphe.inews.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAppStorageNotNull() {
        Assert.assertNotNull("Testing app storage not null", appStorage)
    }

    @Test
    fun testIfToolbarIsDisplayed() {
        onView(ViewMatchers.withId(R.id.appToolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testToolbarOverFlowOptionsMenu() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().context)
    }

    @Test
    fun testBottomBarOverIsDisplayed() {
        onView(ViewMatchers.withId(R.id.appBottomNavigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}