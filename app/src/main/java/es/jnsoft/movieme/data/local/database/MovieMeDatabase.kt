package es.jnsoft.movieme.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.data.local.element.ElementDao

@Database(entities = [Element::class], version = 1, exportSchema = false)
abstract class MovieMeDatabase : RoomDatabase() {

    abstract fun elementDao(): ElementDao

    companion object {

        @Volatile
        private var INSTANCE: MovieMeDatabase? = null

        fun getDatabase(context: Context): MovieMeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieMeDatabase::class.java,
                    "movieMeDatabase.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}