package com.dom.todo.view.container

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dom.todo.R
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView: TextView = view.findViewById(R.id.calendarDayText)
    val dotView: ImageView = view.findViewById(R.id.calendarDayScheduledIcon)
    // Will be set when this container is bound
    lateinit var day: CalendarDay

    init {
        view.setOnClickListener {
            // Use the CalendarDay associated with this container.
        }
        // check schedule is exist
        // if exist, show dotView
        // else, hide dotView

    }



    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}