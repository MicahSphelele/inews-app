package sphe.inews.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sphe.inews.di.AppModule
import sphe.inews.test.BuildConfig
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import javax.inject.Inject
import javax.inject.Named


@UninstallModules(AppModule::class)
@HiltAndroidTest
class SplashActivityTest {

    @get:Rule
    val testRule = HiltAndroidRule(this)

    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage


    @Before
    fun setUp() {
        testRule.inject()
    }

    @Test
    fun testStringAppVersion() {
        ViewMatchers.assertThat("Testing app version",appVersion,containsString(BuildConfig.VERSION_NAME))
    }

    @Test
    fun testAppStorageNotNull() {
        Assert.assertNotNull("Testing app storage not null",appStorage)
    }

    @Test
    fun testAppThemeIsSystemDefault() {
        Assert.assertEquals("Testing app theme is in system default","system_default",appStorage.getStringData(Constants.KEY_THEME, Constants.DEFAULT_THEME))
    }

    @Test
    fun testSplashScreenIsLaunch() {
        launchActivity<SplashActivity>()

    }

    fun tearDown() {
        Intents.release()
    }


}