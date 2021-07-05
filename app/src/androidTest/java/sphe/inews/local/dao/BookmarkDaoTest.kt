package sphe.inews.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sphe.inews.local.room.AppDB
import sphe.inews.models.Bookmark


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
        val bookMark = Bookmark(
            id = 1,
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
        dao.insert(bookMark)

       val bookmarks = dao.getBooMarks()
        assertThat(bookmarks).isNotEmpty()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDBDelete() = runBlockingTest {

        val bookMark = Bookmark(
            id = 1,
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
        dao.insert(bookMark)

        val bookmarks = dao.getBooMarks()
        assertThat(bookmarks).isNotEmpty()

        dao.delete(bookMark)
        assertThat(bookmarks).isNotEmpty()
    }
    
    fun tearDown() {
        database.close()
    }
}