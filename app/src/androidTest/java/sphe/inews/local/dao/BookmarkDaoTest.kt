package sphe.inews.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import sphe.inews.di.AppModule
import sphe.inews.enums.NewsCategory
import sphe.inews.getOrAwaitValue
import sphe.inews.local.room.AppDB
import sphe.inews.models.Bookmark
import sphe.inews.util.Constants

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@UninstallModules(AppModule::class)
@HiltAndroidTest
class BookmarkDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDB
    private lateinit var dao: BookmarkDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries().build()

        dao = database.bookmarkDao()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBInsert() = runBlockingTest {
        val bookMarkEntry = Bookmark(
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
        dao.insert(bookMarkEntry)

       val bookmarks = dao.getBooMarks()
        assertThat(bookmarks).isNotEmpty()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBDelete() = runBlockingTest {

        val bookMarkEntry = Bookmark(
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
        dao.insert(bookMarkEntry)

        val bookmarks = dao.getBooMarks()
        assertThat(bookmarks).isNotEmpty()

        dao.delete(bookMarkEntry)
        assertThat(bookmarks).isNotEmpty()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBGetBookmarkList() = runBlockingTest {
        val bookMarkEntry1 = Bookmark(
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
        val bookMarkEntry2 = Bookmark(
            url = "https://www.news24.com/sport/soccer/euro2020/france-fall-out-adrien-rabiots-mother-clashes-with-mbappe-pogba-families-after-euro-exit-20210630, author=sport, content=In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit.",
            author = "sport",
            content = "Didier Deschamp's side were… [+2150 chars], description=In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit",
            publishedAt = "2021-06-30T09:12:30Z",
            sourceId = "news24",
            sourceName = "News24",
            title = "France fall-out | Adrien Rabiot's mother clashes with Mbappe, Pogba families after Euro exit - News24",
            urlToImage = "https://cdn.24.co.za/files/Cms/General/d/4032/780cff14f0bf419eb2414c9baa69ea19.jpg, category=sports")
        dao.insert(bookMarkEntry1)
        dao.insert(bookMarkEntry2)

        val bookmarks = dao.getBooMarks()
        assertThat(bookmarks).isNotEmpty()
        assertThat(bookmarks.size).isEqualTo(2)
        assertThat(bookmarks).contains(bookMarkEntry1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBGetSingleBookmark() = runBlockingTest {
        val bookMarkEntry = Bookmark(
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

        dao.insert(bookMarkEntry)

        val bookmark = dao.getBooMark(bookMarkEntry.url)

        assertThat(bookmark).isEqualTo(bookMarkEntry)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBGetBooMarksObservedISEmptyAndNotNull() = runBlockingTest {
        val bookmarks = dao.getBooMarksObserved().getOrAwaitValue()
        assertThat(bookmarks).isEmpty()
        assertThat(bookmarks).isNotNull()
        assertThat(bookmarks.size).isEqualTo(0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBGetBooMarksObservedISNotNullOrEmpty() = runBlockingTest {
        val bookMarkEntry1 = Bookmark(
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
        val bookMarkEntry2 = Bookmark(
            url = "https://www.news24.com/sport/soccer/euro2020/france-fall-out-adrien-rabiots-mother-clashes-with-mbappe-pogba-families-after-euro-exit-20210630, author=sport, content=In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit.",
            author = "sport",
            content = "Didier Deschamp's side were… [+2150 chars], description=In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit",
            publishedAt = "2021-06-30T09:12:30Z",
            sourceId = "news24",
            sourceName = "News24",
            title = "France fall-out | Adrien Rabiot's mother clashes with Mbappe, Pogba families after Euro exit - News24",
            urlToImage = "https://cdn.24.co.za/files/Cms/General/d/4032/780cff14f0bf419eb2414c9baa69ea19.jpg, category=sports",
            category = "sports")
        dao.insert(bookMarkEntry1)
        dao.insert(bookMarkEntry2)

        val bookmarks = dao.getBooMarksObserved().getOrAwaitValue()
        assertThat(bookmarks).isNotNull()
        assertThat(bookmarks).isNotEmpty()
        assertThat(bookmarks.size).isEqualTo(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetBookMarksByCategory() = runBlockingTest {
        val bookMarkEntry1 = Bookmark(
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
        val bookMarkEntry2 = Bookmark(
            url = "https://www.news24.com/sport/soccer/euro2020/france-fall-out-adrien-rabiots-mother-clashes-with-mbappe-pogba-families-after-euro-exit-20210630, author=sport, content=In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit.",
            author = "sport",
            content = "Didier Deschamp's side were… [+2150 chars], description=In sensational scenes, France midfielder Adrien Rabiot's mother was shown in video footage clashing with the Mbappe and Pogba families after their shock Euro 2020 exit",
            publishedAt = "2021-06-30T09:12:30Z",
            sourceId = "news24",
            sourceName = "News24",
            title = "France fall-out | Adrien Rabiot's mother clashes with Mbappe, Pogba families after Euro exit - News24",
            urlToImage = "https://cdn.24.co.za/files/Cms/General/d/4032/780cff14f0bf419eb2414c9baa69ea19.jpg, category=sports",
            category = NewsCategory.HEALTH.title.toLowerCase())
        dao.insert(bookMarkEntry1)
        dao.insert(bookMarkEntry2)

        val bookmarks = dao.getBooMarksByCategory(NewsCategory.HEALTH.title.toLowerCase())
        assertThat(bookmarks).isNotEmpty()
        assertThat(bookmarks.size).isEqualTo(1)
        assertThat(bookmarks).contains(bookMarkEntry2)
    }

    fun tearDown() {
        database.close()
    }
}