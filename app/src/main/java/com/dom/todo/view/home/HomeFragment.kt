package com.dom.todo.view.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dom.todo.R
import com.dom.todo.base.BaseFragment
import com.dom.todo.base.BaseRecyclerViewDecoration
import com.dom.todo.databinding.FragmentHomeBinding
import com.dom.todo.extension.ViewExtension.visible
import com.dom.todo.util.EventObserver
import com.dom.todo.view.add.ScheduleAddFragment
import com.dom.todo.view.container.DayViewContainer
import com.dom.todo.view.container.MonthViewContainer
import com.dom.todo.view.home.adapter.ScheduleItemAdapter
import com.dom.todo.view.home.viewmodel.HomeViewModel
import com.dom.todo.view.update.ScheduleUpdateFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.MonthScrollListener
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private var beforeClickedTextView: TextView? = null
    private var storedTextView: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            setUI()
            observing()
            setOnViewClickListener()
            setFragmentResultListener()
        }
    }

    private fun initializeCalendar() {
        binding {
            calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
                override fun create(view: View): DayViewContainer {
                    return DayViewContainer(view)
                }

                override fun bind(container: DayViewContainer, data: CalendarDay) {
                    val textView = container.textView
                    val dotView = container.dotView

                    container.day = data
                    textView.text = data.date.dayOfMonth.toString()

                    textView.setOnClickListener {
                        if (data.position != DayPosition.MonthDate) {
                            return@setOnClickListener
                        }

                        homeViewModel.setSelectedDate(data.date)
                        calendarView.notifyDateChanged(data.date)

                        if (beforeClickedTextView != null) {
                            beforeClickedTextView!!.isSelected = false
                        }
                        textView.isSelected = true
                        beforeClickedTextView = textView
                    }

                    if(storedTextView != null) {
                        Log.e("TAG", "storedTextView is not null: ${storedTextView!!.text}")
                        if(storedTextView!!.text == textView.text) {
                            textView.isSelected = true
                            storedTextView = null
                        }
                    }


                    // 설정 초기 상태 (월이 속하지 않는 날짜는 비활성화)
                    if (data.position != DayPosition.MonthDate) {
                        textView.setTextColor(Color.LTGRAY)
                        dotView.visible(false)
                    } else {
                        textView.setTextColor(Color.BLACK)
                        dotView.visible(true)
                        dotView.isSelected = homeViewModel.allScheduleData.value?.any {
                            it.date == data.date.toString()
                        } == true
                    }
                }
            }

            calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    if (container.titlesContainer.tag == null) {
                        container.titlesContainer.tag = data.yearMonth
                        container.titlesContainer.children.map { it as TextView }
                            .forEachIndexed { index, textView ->
                                val dayOfWeek = daysOfWeek()[index]
                                val title =
                                    dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                                textView.text = title
                            }
                    }
                }
            }

            calendarView.monthScrollListener = object : MonthScrollListener {
                override fun invoke(calendarMonth: CalendarMonth) {
                    tvYear.text = "${calendarMonth.yearMonth.year}"
                    tvMonth.text = "${calendarMonth.yearMonth.monthValue} 월"
                }
            }

            val currentMonth = YearMonth.now()
            val startMonth = currentMonth.minusMonths(100)
            val endMonth = currentMonth.plusMonths(100)
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
        }
    }

    private fun setUI() {
        binding {
            val scheduleItemAdapter = ScheduleItemAdapter { bundle ->
                // check schedule completed
                if (bundle.containsKey("id")) {
                    val id = bundle.getInt("id")
                    val check = bundle.getBoolean("checked")
                    homeViewModel.setCheck(id, check)
                } else if (bundle.containsKey("idValue")) {
                    // delete schedule
                    val id = bundle.getInt("idValue")
                    homeViewModel.deleteSchedule(id) {
                        homeViewModel.setSelectedDate(homeViewModel.selectedDate.value)
                    }
                } else {
                    // update schedule
                    val id = bundle.getInt("updateIdValue")
                    homeViewModel.setUpdateScheduleId(id)
                }
            }

            rvSchedule.adapter = scheduleItemAdapter
            rvSchedule.addItemDecoration(BaseRecyclerViewDecoration(
                firstItemMargin = resources.getDimension(R.dimen.itemFirstLastVerticalMargin).toInt(),
                lastItemMargin = resources.getDimension(R.dimen.itemFirstLastVerticalMargin).toInt(),
                left = resources.getDimension(R.dimen.itemHorizontalMargin).toInt(),
                right = resources.getDimension(R.dimen.itemHorizontalMargin).toInt(),
                top = resources.getDimension(R.dimen.itemVerticalMargin).toInt(),
                bottom = resources.getDimension(R.dimen.itemVerticalMargin).toInt(),
                orientation = LinearLayoutManager.VERTICAL
            ))
        }
    }

    private fun observing() {
        with(homeViewModel) {
            scheduleInitializeCompleteLiveData.observe(
                viewLifecycleOwner,
                EventObserver { success ->
                    if (!success) {
                        Toast.makeText(requireContext(), "일정을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        initializeCalendar()
                    }
                })

            selectedDate.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    dataBinding.addButtonContainer.visible(true)
                    getSelectedDate(data)
                } else {
                    dataBinding.addButtonContainer.visible(false)
                }
            }

            scheduleData.observe(viewLifecycleOwner) { data ->
                if (data.isEmpty()) {
                    dataBinding.tvNoSchedule.visibility = View.VISIBLE
                } else {
                    dataBinding.tvNoSchedule.visibility = View.GONE
                }
                (dataBinding.rvSchedule.adapter as ScheduleItemAdapter).submitList(data)
            }

            updateScheduleId.observe(viewLifecycleOwner) {
                storedTextView = beforeClickedTextView
                ScheduleUpdateFragment().show(childFragmentManager, "ScheduleUpdateFragment")
            }
        }
    }

    private fun setOnViewClickListener() {
        binding {
            addButton.setOnClickListener {
                if (homeViewModel.selectedDate.value == null) {
                    Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    storedTextView = beforeClickedTextView
                    ScheduleAddFragment().show(childFragmentManager, "ScheduleAddFragment")
                }
            }
        }
    }

    private fun setFragmentResultListener() {
        childFragmentManager.setFragmentResultListener(
            "refreshKey",
            viewLifecycleOwner
        ) { key, bundle ->
            if (bundle.getBoolean("needRefresh", false)) {
                homeViewModel.getAllScheduleData()
                homeViewModel.getSelectedDate(homeViewModel.selectedDate.value)
                bundle.clear()
            }
        }
    }
}
