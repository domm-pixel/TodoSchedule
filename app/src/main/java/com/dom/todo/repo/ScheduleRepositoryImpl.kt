package com.dom.todo.repo

import com.dom.todo.dao.ScheduleDao
import com.dom.todo.model.schedule.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDao: ScheduleDao
) : ScheduleRepository {

    override suspend fun getScheduleData(): List<Schedule> = withContext(Dispatchers.IO) {
        scheduleDao.getAll()
    }

    override suspend fun getScheduleDataByDate(date: String): List<Schedule> = withContext(Dispatchers.IO) {
        scheduleDao.getAllByDate(date)
    }

    override suspend fun getAllByDateAndChecked(date: String, checked: Boolean): List<Schedule> = withContext(Dispatchers.IO) {
        scheduleDao.getAllByDateAndChecked(date, checked)
    }

    override suspend fun insertScheduleData(data: Schedule) = withContext(Dispatchers.IO) {
        scheduleDao.addSchedule(data)
    }

    override suspend fun updateScheduleData(data: Schedule) = withContext(Dispatchers.IO) {
        scheduleDao.updateSchedule(data)
    }

    override suspend fun deleteScheduleData(id: Int) = withContext(Dispatchers.IO) {
        scheduleDao.deleteSchedule(id)
    }

    override suspend fun setCheck(id: Int, check: Boolean) = withContext(Dispatchers.IO) {
        scheduleDao.setCheck(id, check)
    }
}