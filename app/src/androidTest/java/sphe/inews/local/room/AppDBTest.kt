package sphe.inews.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runners.MethodSorters
import sphe.inews.di.AppModule
import sphe.inews.models.Bookmark
import sphe.inews.util.Constants

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@UninstallModules(AppModule::class)
@HiltAndroidTest
class AppDBTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDB

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries().build()

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseNotOpen() {
        assertThat(database.isOpen).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseOpen() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(database.isOpen).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabaseVersionIsCurrent() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.version).isEqualTo(Constants.DB_VERSION)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabasePathIsMemory() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.path).isEqualTo(":memory:")
    }

    @After
    fun tearDown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    private fun executeDatabaseFunction() = runBlockingTest {
        val bookMark = Bookmark(
            url = "https://www.news24.com/sport/soccer/euro2020/france-fall-out-adrien-rabiots-mother-clashes-with-mbappe-pogba-families-after-euro-exit-20210630",
            author = "sport",
            content = "In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit. Didier Deschamp's side were… [+2150 chars]",
            description = "In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit.",
            publishedAt = "2021-06-30T09:12:30Z",
            sourceId = "news24",
            sourceName = "News24",
            title = "France fall -out | Adrien Rabiot's mother clashes with Mbappe, Pogba families after Euro exit - News24",
            urlToImage = "https://cdn.24.co.za/files/Cms/General/d/4032/780cff14f0bf419eb2414c9baa69ea19.jpg",
            category="sports")
        database.bookmarkDao().insert(bookMark)
    }
}