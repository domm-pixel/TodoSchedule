package com.dom.todo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dom.todo.model.schedule.Schedule

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM Schedule")
    fun getAll(): List<Schedule>

    //query which get all by date
    @Query("SELECT * FROM Schedule WHERE date = :date")
    fun getAllByDate(date: String): List<Schedule>

    //query which get all by date and checked
    @Query("SELECT * FROM Schedule WHERE date = :date AND checked = :checked")
    fun getAllByDateAndChecked(date: String, checked: Boolean): List<Schedule>

    //query which add schedule
    @Insert
    fun addSchedule(schedule: Schedule)

    //query which update schedule
    @Update
    fun updateSchedule(schedule: Schedule)

    //query which delete schedule
    @Query("DELETE FROM Schedule WHERE id = :id")
    fun deleteSchedule(id: Int)

    //query which set check
    @Query("UPDATE Schedule SET checked = :check WHERE id = :id")
    fun setCheck(id: Int, check: Boolean)
}