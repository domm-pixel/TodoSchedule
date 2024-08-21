package com.dom.todo.view.update.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dom.todo.base.BaseViewModel
import com.dom.todo.model.schedule.Schedule
import com.dom.todo.repo.ScheduleRepository
import com.dom.todo.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleUpdateViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): BaseViewModel() {

    val scheduleDetailLiveData = MutableLiveData<Schedule?>()

    val viewClickLiveData = MutableLiveData<Event<Int>>()

    val updateScheduleId = MutableLiveData<Int>()

    init {
        scheduleDetailLiveData.value = null
    }

    fun getScheduleDetail(id: Int) {
        viewModelScope.launch {
            scheduleDetailLiveData.value = scheduleRepository.getScheduleDataById(id)
            updateScheduleId.value = id
            Log.e("TAG KDH", "ScheduleUpdateViewModel getScheduleDetail: ${scheduleDetailLiveData.value}")
        }
    }

    fun updateScheduleData(title: String, contents:String, callback: () -> Unit) {
        viewModelScope.launch {
            scheduleRepository.updateScheduleData(
                id = updateScheduleId.value!!,
                title = title,
                contents = contents
            )
            callback()
        }
    }

    fun onViewClick(viewId: Int) {
        viewClickLiveData.value = Event(viewId)
    }
}