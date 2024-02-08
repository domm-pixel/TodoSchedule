package com.dom.todo.di

import android.content.Context
import androidx.room.Room
import com.dom.todo.model.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val SCHEDULE_DATABASE_NAME = "ScheduleDataBase"


    private lateinit var appDB: AppDatabase

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext app : Context
    ): AppDatabase {
        appDB = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            SCHEDULE_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        return appDB
    }

    @Provides
    @Singleton
    fun provideAlarmDao(db: AppDatabase) = db.scheduleDao()

}