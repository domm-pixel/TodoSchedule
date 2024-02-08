package com.dom.todo.view.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dom.todo.R
import com.dom.todo.base.BaseFragment
import com.dom.todo.databinding.FragmentHomeBinding
import com.dom.todo.view.container.DayViewContainer
import com.dom.todo.view.container.MonthViewContainer
import com.dom.todo.view.home.adapter.ScheduleItemAdapter
import com.dom.todo.view.home.viewmodel.HomeViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {

            val scheduleItemAdapter = ScheduleItemAdapter { bundle ->
                Log.e("TAG KDH", "bundle : $bundle")
                if (bundle.containsKey("id")) {
                    val id = bundle.getInt("id")
                    val check = bundle.getBoolean("checked")
                    Log.e("TAG KDH", "id : $id, check : $check")
                    homeViewModel.setCheck(id, check)

                } else if (bundle.containsKey("idValue")) {
                    val id = bundle.getInt("idValue")
                    homeViewModel.deleteSchedule(id) {
                        homeViewModel.setSelectedDate(homeViewModel.selectedDate.value)
                    }
                }

            }

            rvSchedule.adapter = scheduleItemAdapter

            calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
                // Called only when a new container is needed.
                override fun create(view: View) = DayViewContainer(view)

                // Called every time we need to reuse a container.
                override fun bind(container: DayViewContainer, data: CalendarDay) {
                    val textView = container.textView
                    // Set the calendar day for this container.
                    container.day = data
                    // Set the date text
                    textView.text = data.date.dayOfMonth.toString()

                    textView.setOnClickListener {
                        // Check the day position as we do not want to select in or out dates.
                        Log.e("TAG KDH", "data : ${data.date}")
                        Log.e("TAG KDH", "data : ${data.position}")
                        val selectedDate = if (data.position == DayPosition.MonthDate) {
                            data.date
                        } else {
                            null
                        }

                        homeViewModel.setSelectedDate(selectedDate)

                        textView.visibility = View.VISIBLE
                        if (data.date == homeViewModel.selectedDate.value) {
                            // set the selected date
//                            homeViewModel.setSelectedDate(selectedDate!!)

                            // If this is the selected date, show a round background and change the text color.
                            textView.setTextColor(Color.WHITE)
                            textView.setBackgroundResource(R.drawable.ic_launcher_background)
                        } else {
//                            homeViewModel.setSelectedDate(null)
                            // If this is NOT the selected date, remove the background and reset the text color.
                            textView.setTextColor(Color.BLACK)
                            textView.background = null
                        }


                        calendarView.notifyDateChanged(homeViewModel.selectedDate.value!!)
                    }

                    // set initial state about month state
                    if (data.position == DayPosition.MonthDate) {
                        textView.setTextColor(Color.BLACK)

                    } else {
                        textView.setTextColor(Color.LTGRAY)
                    }
                }
            }

            calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Remember that the header is reused so this will be called for each month.
                    // However, the first day of the week will not change so no need to bind
                    // the same view every time it is reused.
                    if (container.titlesContainer.tag == null) {
                        container.titlesContainer.tag = data.yearMonth
                        container.titlesContainer.children.map { it as TextView }
                            .forEachIndexed { index, textView ->
                                val dayOfWeek = daysOfWeek()[index]
                                val title =
                                    dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                                Log.e("TAG KDH", "title : $title")
                                textView.text = title
                                // In the code above, we use the same `daysOfWeek` list
                                // that was created when we set up the calendar.
                                // However, we can also get the `daysOfWeek` list from the month data:
                                // val daysOfWeek = data.weekDays.first().map { it.date.dayOfWeek }
                                // Alternatively, you can get the value for this specific index:
                                // val dayOfWeek = data.weekDays.first()[index].date.dayOfWeek
                            }
                    }
                }
            }


            val currentMonth = YearMonth.now()
            val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
            val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
//            val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
            val daysOfWeek = daysOfWeek()
            calendarView.setup(startMonth, endMonth, daysOfWeek.first())
            calendarView.scrollToMonth(currentMonth)

            val titlesContainer = titlesContainer as ViewGroup
            titlesContainer.children
                .map { it as TextView }
                .forEachIndexed { index, textView ->
                    val dayOfWeek = daysOfWeek[index]
                    val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    textView.text = title
                }

            // observe
            homeViewModel.selectedDate.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    Log.e("TAG KDH", "selectedDate : $data")
                    homeViewModel.getSelectedDate(data)
                } else {
                    scheduleItemAdapter.submitList(emptyList())
                }
            }



            homeViewModel.scheduleData.observe(viewLifecycleOwner) { data ->
                Log.e("TAG KDH", "scheduleData : $data")
                if (data.isEmpty()) {
                    tvNoSchedule.visibility = View.VISIBLE
                } else {
                    tvNoSchedule.visibility = View.GONE
                }
                scheduleItemAdapter.submitList(data)
            }

            addButton.setOnClickListener {
                if (homeViewModel.selectedDate.value == null) {
                    Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(
                        R.id.action_fragment_home_to_fragment_add_schedule,
                        args = Bundle().apply {
                            putString("date", homeViewModel.selectedDate.value.toString())
                        })
                }
            }
        }
    }
}