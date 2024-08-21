package com.dom.todo.view.update

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.dom.todo.R
import com.dom.todo.base.BaseDialogFragment
import com.dom.todo.databinding.FragmentAddScheduleBinding
import com.dom.todo.databinding.FragmentUpdateScheduleBinding
import com.dom.todo.model.schedule.Schedule
import com.dom.todo.util.EventObserver
import com.dom.todo.view.home.viewmodel.HomeViewModel
import com.dom.todo.view.update.viewmodel.ScheduleUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleUpdateFragment: BaseDialogFragment<FragmentUpdateScheduleBinding>(R.layout.fragment_update_schedule) {

    private val homeViewModel: HomeViewModel by viewModels (
        ownerProducer = { requireParentFragment() }
    )

    private val scheduleUpdateViewModel: ScheduleUpdateViewModel by viewModels()

    private var refreshNeedFlag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {

            viewModel = scheduleUpdateViewModel

            homeViewModel.updateScheduleId.value?.let {
                Log.e("TAG KDH", "ScheduleUpdateFragment onViewCreated: $it")
                scheduleUpdateViewModel.getScheduleDetail(it)
            }?: run {
                Toast.makeText(requireContext(), "일정을 선택해주세요.", Toast.LENGTH_SHORT).show()
                dismiss()
            }

            observeViewModel()

        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private fun observeViewModel() {
        with(scheduleUpdateViewModel) {
            viewClickLiveData.observe(viewLifecycleOwner, EventObserver { viewId ->
                when(viewId) {
                    R.id.ivBack -> {
                        dialog?.dismiss()
                    }
                    R.id.btnSave -> {
                        scheduleUpdateViewModel.updateScheduleData(
                            title = dataBinding.etTitle.text.toString(),
                            contents = dataBinding.etContents.text.toString()
                        ) {
                            Toast.makeText(requireContext(), "일정이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                            refreshNeedFlag = true
                            dialog?.dismiss()
                        }
                    }
                }

            })

            scheduleDetailLiveData.observe(viewLifecycleOwner) { detail ->
                Log.e("TAG KDH", "ScheduleUpdateFragment observeViewModel: $detail")
                binding {
                    etTitle.setText(detail?.title)
                    etContents.setText(detail?.contents)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(refreshNeedFlag) {
            setFragmentResult()
        }
    }

    private fun setFragmentResult() {
        setFragmentResult("refreshKey", Bundle().apply {
            putBoolean("needRefresh", true)
        })
    }
}