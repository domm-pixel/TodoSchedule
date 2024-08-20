package com.dom.todo.view.add

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dom.todo.R
import com.dom.todo.base.BaseDialogFragment
import com.dom.todo.databinding.FragmentAddScheduleBinding
import com.dom.todo.model.schedule.Schedule
import com.dom.todo.util.EventObserver
import com.dom.todo.view.add.viewmodel.AddViewModel
import com.dom.todo.view.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleAddFragment: BaseDialogFragment<FragmentAddScheduleBinding>(R.layout.fragment_add_schedule) {

    private val homeViewModel: HomeViewModel by viewModels (
        ownerProducer = { requireParentFragment() }
    )
    private val addViewModel: AddViewModel by viewModels()

//    private val db = Room.databaseBuilder(
//        requireContext(),
//        AppDatabase::class.java, "schedule-todo-db"
//    ).build()
//    private val scheduleDao = db.scheduleDao()

//    val db = AppDatabase.getInstance(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG KDH", "ScheduleAddFragment onViewCreated: ${homeViewModel.selectedDate.value}")

        // get date from homeViewModel which is selected by user
        val date: String = homeViewModel.selectedDate.value.toString()

        // if date is empty, show toast and popBackStack
        if(date.isEmpty()) {
            Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            dismiss()
        }


        binding {
            viewModel = addViewModel
            showSoftKeyboard(etTitle)

            addViewModel.backClickEventLiveData.observe(viewLifecycleOwner, EventObserver { viewId ->
                when(viewId) {
                    R.id.ivBack -> {
                        dialog?.dismiss()
                    }
                    R.id.btnSave -> {
                        val title = etTitle.text.toString()
                        val contents = etContents.text.toString()

                        // if title or contents is empty, show toast and return
                        if(title == "" || contents == "") {
                            Toast.makeText(requireContext(), "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                            return@EventObserver
                        }

                        // create schedule object
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
                                dismiss()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onStart() {
        super.onStart()
        // set match parent
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        setFragmentResult()
    }

    private fun setFragmentResult() {
        setFragmentResult("refreshKey", Bundle().apply {
            putBoolean("needRefresh", true)
        })
    }
}