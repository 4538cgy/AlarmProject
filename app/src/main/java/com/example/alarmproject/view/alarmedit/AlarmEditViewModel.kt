package com.example.alarmproject.view.alarmedit

import androidx.lifecycle.MutableLiveData
import com.example.alarmproject.util.time.formatHourMinute
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmEditViewModel @Inject constructor() : BaseViewModel() {

    val nextAlarmNotice = MutableLiveData<Long>()
    val timeText = MutableLiveData<String>()
    val pickTime = MutableLiveData<Pair<Int,Int>>()

    fun getNextAlarmText() = NEXT_ALARM_NOTICE_FIRST_TEXT + nextAlarmNotice.value?.let { formatHourMinute(it) } + NEXT_ALARM_NOTICE_LAST_TEXT



    companion object {
        const val NEXT_ALARM_NOTICE_FIRST_TEXT = "지금부터 "
        const val NEXT_ALARM_NOTICE_LAST_TEXT = " 뒤에 알람이 울림"
    }
}