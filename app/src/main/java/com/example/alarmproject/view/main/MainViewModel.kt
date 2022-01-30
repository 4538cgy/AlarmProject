package com.example.alarmproject.view.main

import androidx.lifecycle.MutableLiveData
import com.example.alarmproject.model.alarm.VHAlarmProperty
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    val selectedPropertyTitle = MutableLiveData<VHAlarmProperty>()
}