package com.dom.todo.view.add

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dom.todo.R
import com.dom.todo.base.BaseFragment
import com.dom.todo.databinding.FragmentAddScheduleBinding
import com.dom.todo.model.schedule.Schedule
import com.dom.todo.view.add.viewmodel.AddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleAddFragment:BaseFragment<FragmentAddScheduleBinding>(R.layout.fragment_add_schedule) {

    private val addViewModel: AddViewModel by viewModels()

//    private val db = Room.databaseBuilder(
//        requireContext(),
//        AppDatabase::class.java, "schedule-todo-db"
//    ).build()
//    private val scheduleDao = db.scheduleDao()

//    val db = AppDatabase.getInstance(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = arguments?.getString("date")

        binding {
            btnSave.setOnClickListener {
                /**
                    @ColumnInfo(name = "date") val date: String?,
                    @ColumnInfo(name = "title") val title: String?,
                    @ColumnInfo(name = "contents") val contents: String?,
                    @ColumnInfo(name = "checked") val checked: Boolean?
                * */
                val title = etTitle.text.toString()
                val contents = etContents.text.toString()
                val schedule = Schedule(
                    date = date,
                    title = title,
                    contents = contents,
                    checked = false
                )
                addViewModel.insertScheduleData(schedule) {
                    Log.e("TAG KDH",schedule.toString())
                    if(etTitle.text.toString().isNotEmpty() && etContents.text.toString().isNotEmpty()) {
                        Toast.makeText(requireContext(), "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}