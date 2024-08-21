package com.dom.todo.view.home.itemviewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dom.todo.base.BaseItemViewModel
import com.dom.todo.model.schedule.Schedule
import javax.inject.Inject

class ScheduleItemViewModel @Inject constructor(
    scheduleItemData: Schedule,
    private val adapterCallback: (Bundle) -> Unit
) : BaseItemViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _contents = MutableLiveData<String>()
    val contents: LiveData<String> = _contents

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean> = _isChecked

    private val idValue : Int

    init {
        _title.value = scheduleItemData.title ?: ""
        _contents.value = scheduleItemData.contents ?: ""
        _isChecked.value = scheduleItemData.checked ?: false
        idValue = scheduleItemData.id
    }

    fun onClickItem() {
        adapterCallback(Bundle().apply {
            // put checked
            putInt("id", idValue)
            putBoolean("checked", !isChecked.value!!)
            _isChecked.value = !isChecked.value!!
        })
    }

    fun onClickDeleteItem() {
        adapterCallback(Bundle().apply {
            // put checked
            putInt("idValue", idValue)
        })
    }

    fun onClickUpdateItem() {
        adapterCallback(Bundle().apply {
            // put checked
            putInt("updateIdValue", idValue)
        })
    }
}