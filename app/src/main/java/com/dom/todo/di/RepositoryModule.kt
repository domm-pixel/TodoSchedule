package com.dom.todo.di

import com.dom.todo.dao.ScheduleDao
import com.dom.todo.repo.ScheduleRepository
import com.dom.todo.repo.ScheduleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAlarmRepository(
        scheduleDao: ScheduleDao,
    ) : ScheduleRepository = ScheduleRepositoryImpl(scheduleDao)
}