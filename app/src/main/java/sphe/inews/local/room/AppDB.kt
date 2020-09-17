package sphe.inews.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import sphe.inews.local.dao.BookmarkDao
import sphe.inews.models.Bookmark

@Database(entities = [Bookmark::class],version = 2,exportSchema = false)
abstract class AppDB : RoomDatabase(){

    abstract fun bookmarkDao() : BookmarkDao

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

        private val callBack = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }

        }
    }

}