package com.dom.todo.view.add.viewmodel

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
class AddViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : BaseViewModel() {

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> = _content

    val backClickEventLiveData = MutableLiveData<Event<Int>>()

    fun setDate(date: String) {
        _date.value = date
    }

    fun setTime(time: String) {
        _time.value = time
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun insertScheduleData(schedule: Schedule, callback: () -> Unit) {
        viewModelScope.launch {
            scheduleRepository.insertScheduleData(
                schedule
            )
            callback()
        }
    }

    fun onViewClick(viewId: Int) {
        backClickEventLiveData.value = Event(viewId)
    }

}