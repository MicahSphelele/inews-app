package sphe.inews.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sphe.inews.data.local.dao.BookmarkDao
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.util.Constants

@Database(entities = [Bookmark::class], version = Constants.DB_VERSION, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {

        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {

            return instance ?: synchronized(AppDB::class.java) {
                instance ?: buildAppDB(context).also {
                    instance = it
                }
            }

        }

        private fun buildAppDB(context: Context): AppDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java, "appDB.db"
            )
                .fallbackToDestructiveMigration()
                .addCallback(callBack)
                .build()
        }

        private val callBack = object : RoomDatabase.Callback() {

        }

//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE ${Constants.TABLE_BOOKMARK} ADD COLUMN ${Constants.CATEGORY} TEXT")
//            }
//        }
    }

}