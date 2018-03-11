package com.playlistappkotlin.di.db

import android.arch.persistence.room.Room
import android.content.Context
import com.playlistappkotlin.data.db.AppDatabase
import com.playlistappkotlin.data.db.DbHelper
import com.playlistappkotlin.data.db.IDbHelper
import com.playlistappkotlin.data.db.model.TrackDao
import com.playlistappkotlin.di.ApplicationContext
import com.playlistappkotlin.ext.Constants.Companion.DATABASE_FILE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module which provides SQLite database.
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun providesDataBase(@ApplicationContext context: Context, @DatabaseInfo dbName: String): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java!!, dbName)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @DatabaseInfo
    internal fun provideDatabaseName(): String {
        return DATABASE_FILE_NAME
    }

    @Provides
    @Singleton
    internal fun provideTrackDao(database: AppDatabase): TrackDao {
        return database.trackDao()
    }

    @Provides
    @Singleton
    internal fun provideDbHelper(trackDao: TrackDao): IDbHelper {
        return DbHelper(trackDao)
    }
}
