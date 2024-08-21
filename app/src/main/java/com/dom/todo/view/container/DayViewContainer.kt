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

    var day: CalendarDay? = null

    // 일정 존재 여부를 나타내는 속성
    var hasSchedule: Boolean = false
        set(value) {
            field = value
            updateDotViewVisibility()
        }

    init {
        view.setOnClickListener {
            day?.let {
                // 날짜 선택 시 수행할 동작을 여기서 처리할 수 있습니다.
            }
        }
    }

    // dotView의 가시성을 설정하는 메서드
    private fun updateDotViewVisibility() {
        dotView.visibility = if (hasSchedule) View.VISIBLE else View.INVISIBLE
    }
}
