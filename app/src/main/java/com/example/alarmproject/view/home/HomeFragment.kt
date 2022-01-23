package com.example.alarmproject.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.alarmproject.R
import com.example.alarmproject.databinding.FragmentHomeBinding
import com.example.alarmproject.databinding.VhAlarmTagBinding
import com.example.alarmproject.model.alarm.AlarmTag
import com.example.alarmproject.util.system.Logger
import com.example.alarmproject.BR
import com.example.alarmproject.databinding.VhAlarmItemBinding
import com.example.alarmproject.model.alarm.AlarmList
import com.example.alarmproject.util.extension.repeatOnStarted
import com.example.alarmproject.view.base.BaseFragment
import com.example.alarmproject.view.base.BaseRecyclerView
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    private val tagAdapter = BaseRecyclerView.Adapter<AlarmTag, VhAlarmTagBinding>(
        layoutResId = R.layout.vh_alarm_tag,
        bindingVariableId = BR.data,
        callBack = { }
    )

    private val alarmAdapter = BaseRecyclerView.Adapter<AlarmTag, VhAlarmItemBinding>(
        layoutResId = R.layout.vh_alarm_item,
        bindingVariableId = BR.data,
        callBack = { }
    )

    private val etcAdapter = BaseRecyclerView.Adapter<AlarmTag, VhAlarmItemBinding>(
        layoutResId = R.layout.vh_alarm_item,
        bindingVariableId = BR.data,
        callBack = { }
    )

    private fun recyclerViewDataTest() {
        val titleList = arrayListOf("#07시 주간 알람", "#20시 운동 알람", "#23시 수면 알람", "#14시 월수금 학원 알람", "#07시 행복한 하루 알람")
        tagAdapter.replaceAll(createTestData(titleList))
    }

    private fun createTestData(title: List<String>): List<AlarmTag> {
        var testDataList = arrayListOf<AlarmTag>()
        title.forEach {
            val data = AlarmTag()
            data.title = it
            testDataList.add(data)
        }
        return testDataList
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.process(this.toString())
        getTestAlarmData()
        initCollector()
        initAdapter()

        binding.fabAdd.setOnClickListener { goAddAlarm() }
    }

    private fun getTestAlarmData() {
        repeatOnStarted {
            viewModel.getAlarmData()
            viewModel.getAlarmData2()
        }
    }

    private fun initCollector() {
        repeatOnStarted {
            launch {
                viewModel.alarmDataFlow.collect {
                    flowHandler(it)
                }
            }
            launch {
                viewModel.alarmDataFlow2.collect {
                    flowHandler(it)
                }
            }
        }
    }

    private fun flowHandler(it: BaseViewModel.Result<AlarmList>) {
        when (it) {
            is BaseViewModel.Result.Success -> {
                replaceAdapterData(it.data)
            }
            is BaseViewModel.Result.Error -> {

            }
        }
    }

    private fun initAdapter() {
        //태그 리사이클러뷰
        binding.rvTag.apply {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            layoutManager = staggeredGridLayoutManager
            adapter = tagAdapter
        }
        recyclerViewDataTest()
        //기상 알람 리사이클러뷰
        binding.rvMorningAlarmList.apply { adapter = alarmAdapter }

        //기타 알람 리사이클러뷰
        binding.rvEtcList.apply { adapter = etcAdapter }
    }

    private fun replaceAdapterData(data: AlarmList) {
        alarmAdapter.replaceAll(data.alarmList)

        if (etcAdapter.itemCount < 1)
            etcAdapter.replaceAll(data.alarmList)
    }

    private fun goAddAlarm() {
        Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_alarmEditFragment)
    }
}