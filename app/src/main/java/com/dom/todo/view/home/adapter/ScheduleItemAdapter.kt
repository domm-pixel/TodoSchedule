package com.dom.todo.view.home.adapter

import android.os.Bundle
import android.view.ViewGroup
import com.dom.todo.BR
import com.dom.todo.R
import com.dom.todo.base.BaseAdapter
import com.dom.todo.databinding.ItemDayScheduleBinding
import com.dom.todo.model.schedule.Schedule
import com.dom.todo.view.home.itemviewmodel.ScheduleItemViewModel

class ScheduleItemAdapter(
    private val fragmentCallback: (Bundle) -> Unit
) : BaseAdapter<Schedule, ScheduleItemViewModel, ItemDayScheduleBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ScheduleItemViewHolder(parent, R.layout.item_day_schedule)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewHolder = holder as ScheduleItemViewHolder
        viewHolder.bindItem(
            ScheduleItemViewModel(
                getItem(position),
                fragmentCallback
            )
        )
    }

    inner class ScheduleItemViewHolder(parent: ViewGroup, itemView: Int) :
        BaseViewHolder(BR.scheduleItemVm, parent, itemView)
}