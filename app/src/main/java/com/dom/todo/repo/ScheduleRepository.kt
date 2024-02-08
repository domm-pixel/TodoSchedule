package com.dom.todo.repo

import com.dom.todo.model.schedule.Schedule

interface ScheduleRepository {

    // get all schedule data
    suspend fun getScheduleData() : List<Schedule>

    // get schedule data by date
    suspend fun getScheduleDataByDate(date : String) : List<Schedule>

    // update check value
    suspend fun setCheck(id: Int, check: Boolean)

    // get all by date and checked
    suspend fun getAllByDateAndChecked(date : String, checked : Boolean) : List<Schedule>

    // insert schedule data
    suspend fun insertScheduleData(data : Schedule)

    // update schedule data
    suspend fun updateScheduleData(data : Schedule)

    // delete schedule data by id
    suspend fun deleteScheduleData(id: Int)

}