package es.jnsoft.movieme.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.jnsoft.movieme.data.local.database.MovieMeDatabase
import es.jnsoft.movieme.data.local.element.ElementDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieMeDatabase {
        return Room.databaseBuilder(
            context,
            MovieMeDatabase::class.java,
            "movieMeDatabase.db"
        ).build()
    }

    @Provides
    fun provideElementDao(database: MovieMeDatabase): ElementDao {
        return database.elementDao()
    }
}