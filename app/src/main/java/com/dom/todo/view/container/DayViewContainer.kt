package com.dom.todo.view.container

import android.view.View
import android.widget.TextView
import com.dom.todo.R
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)
    // Will be set when this container is bound
    lateinit var day: CalendarDay

    init {
        view.setOnClickListener {
            // Use the CalendarDay associated with this container.
        }
    }



    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}