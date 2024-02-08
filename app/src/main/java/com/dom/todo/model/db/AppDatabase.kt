package com.dom.todo.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dom.todo.dao.ScheduleDao
import com.dom.todo.model.schedule.Schedule

@Database(entities = [Schedule::class], version = 2,  exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}