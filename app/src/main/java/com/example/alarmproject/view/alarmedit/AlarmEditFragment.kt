package com.example.alarmproject.view.alarmedit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.alarmproject.BR
import com.example.alarmproject.R
import com.example.alarmproject.databinding.FragmentAlarmEditBinding
import com.example.alarmproject.databinding.VhAlarmPropertyBinding
import com.example.alarmproject.model.alarm.VHAlarmProperty
import com.example.alarmproject.model.alarm.VHAlarmWeekend
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_FRIDAY
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_MONDAY
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_SATURDAY
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_SUNDAY
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_THURSDAY
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_TUESDAY
import com.example.alarmproject.model.alarm.VHAlarmWeekend.WeekendProperty.Companion.PROPERTY_WEDNESDAY
import com.example.alarmproject.util.time.getNextTime
import com.example.alarmproject.view.base.BaseFragment
import com.example.alarmproject.view.base.BaseRecyclerView
import com.example.alarmproject.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmEditFragment : BaseFragment<FragmentAlarmEditBinding, AlarmEditViewModel>(R.layout.fragment_alarm_edit) {

    override val viewModel: AlarmEditViewModel by viewModels()
    private val activityViewModels : MainViewModel by activityViewModels()

    private val propertyAdapter = BaseRecyclerView.Adapter<VHAlarmProperty, VhAlarmPropertyBinding>(
        layoutResId = R.layout.vh_alarm_property,
        bindingVariableId = BR.data,
        callBack = { onTouchProperty(it) }
    )

    private val weekendAdapter = BaseRecyclerView.Adapter<VHAlarmWeekend, VhAlarmPropertyBinding>(
        layoutResId = R.layout.vh_alarm_weeked,
        bindingVariableId = BR.data,
        callBack = { onTouchWeekend(it) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        binding.tvNextAlarmNotice.text = "지금부터 ${getNextTime(binding.tpTime.hour,binding.tpTime.minute)} 뒤에 알림"

        binding.ibBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_alarmEditFragment_to_homeFragment)
        }
        binding.tpTime.setOnTimeChangedListener { timePicker, i, i2 ->
            viewModel.pickTime.value = Pair(i,i2)
            binding.tvNextAlarmNotice.text = "지금부터 ${getNextTime(i,i2)} 뒤에 알림"
        }
    }

    private fun initAdapter() {
        binding.rvSettingItem.apply {
            adapter = propertyAdapter
        }
        binding.rvDayofweekend.apply {
            adapter = weekendAdapter
        }
        initProperty()
        initWeekend()
    }

    private fun initProperty() {
        val propertyList = arrayListOf<VHAlarmProperty>()

        propertyList.add(VHAlarmProperty("사운드", "사이렌 소리"))
        propertyList.add(VHAlarmProperty("다시 울림 간", "5"))
        propertyList.add(VHAlarmProperty("그룹", "기상"))
        propertyList.add(VHAlarmProperty("레이블", "주간"))
        propertyAdapter.replaceAll(propertyList)
        propertyAdapter.notifyDataSetChanged()
    }

    private fun initWeekend() {
        val weekendList = arrayListOf<VHAlarmWeekend>()
        weekendList.apply {
            add(VHAlarmWeekend(PROPERTY_MONDAY, false))
            add(VHAlarmWeekend(PROPERTY_TUESDAY, false))
            add(VHAlarmWeekend(PROPERTY_WEDNESDAY, false))
            add(VHAlarmWeekend(PROPERTY_THURSDAY, false))
            add(VHAlarmWeekend(PROPERTY_FRIDAY, false))
            add(VHAlarmWeekend(PROPERTY_SATURDAY, false))
            add(VHAlarmWeekend(PROPERTY_SUNDAY, false))
        }
        weekendAdapter.replaceAll(weekendList)
        weekendAdapter.notifyDataSetChanged()
    }

    private fun goEditProperty() {
        Navigation.findNavController(binding.root).navigate(R.id.action_alarmEditFragment_to_alarmEditPropertyFragment)
    }

    private fun onTouchProperty(data: Pair<VHAlarmProperty, Int>) {
        activityViewModels.selectedPropertyTitle.value = data.first
        goEditProperty()
        println("테스트 ${data.first}   ${data.second}")
    }

    private fun onTouchWeekend(data: Pair<VHAlarmWeekend, Int>) {
        println("테스트 ${data.first}   ${data.second}")
    }

}