package com.dom.todo.view.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dom.todo.base.BaseViewModel
import com.dom.todo.model.schedule.Schedule
import com.dom.todo.repo.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): BaseViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate?>()
    val selectedDate: LiveData<LocalDate?> = _selectedDate

    private val _scheduleData = MutableLiveData<List<Schedule>>()
    val scheduleData: LiveData<List<Schedule>> = _scheduleData

    fun setSelectedDate(date: LocalDate?) {
        _selectedDate.value = date
    }
    fun getSelectedDate(date: LocalDate?) {
        viewModelScope.launch {
            _scheduleData.value = scheduleRepository.getScheduleDataByDate(date.toString())
        }
    }

    fun setCheck(id: Int, check: Boolean) {
        viewModelScope.launch {
            scheduleRepository.setCheck(id, check)
        }
    }

    fun deleteSchedule(id: Int, callback: () -> Unit) {
        viewModelScope.launch {
            scheduleRepository.deleteScheduleData(id)
        }
        callback()
    }
}