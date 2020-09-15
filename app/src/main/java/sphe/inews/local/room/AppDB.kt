package sphe.inews.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

abstract class AppDB : RoomDatabase(){
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