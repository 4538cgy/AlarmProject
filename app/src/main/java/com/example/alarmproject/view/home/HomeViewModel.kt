package com.example.alarmproject.view.home

import androidx.lifecycle.ViewModel
import com.example.alarmproject.model.alarm.AlarmList
import com.example.alarmproject.model.alarm.AlarmTag
import com.example.alarmproject.util.flow.MutableEventFlow
import com.example.alarmproject.util.flow.asEventFlow
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    //테스트용 데이터
    private val _alarmDataFlow = MutableEventFlow<Result<AlarmList>>()
    val alarmDataFlow = _alarmDataFlow.asEventFlow()

    private val _alarmDataFlow2 = MutableEventFlow<Result<AlarmList>>()
    val alarmDataFlow2 = _alarmDataFlow2.asEventFlow()

    suspend fun getAlarmData() {
        println("테스트 getAlarmData")
        _alarmDataFlow.emit(Result.Success(alarmTestData()))
    }
    suspend fun getAlarmData2(){
        println("테스트 getAlarmData2")
        _alarmDataFlow2.emit(Result.Success(alarmTestData2()))
    }

    private fun alarmTestData() : AlarmList{
        println("테스트 alarmTestData")
        val list = AlarmList()
        val alarmList = list.alarmList
        alarmList.add(AlarmTag("tttt",25200000))
        alarmList.add(AlarmTag("ttt2", 27000000))

        return list
    }

    private fun alarmTestData2() : AlarmList{
        println("테스트 alarmTestData2")
        val list = AlarmList()
        val alarmList = list.alarmList
        alarmList.add(AlarmTag("tttt2222",70200000))
        alarmList.add(AlarmTag("ttt2222", 54000000))

        return list
    }





}