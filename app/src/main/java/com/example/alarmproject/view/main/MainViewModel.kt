package com.example.alarmproject.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.model.alarm.VHAlarmProperty
import com.example.alarmproject.model.user.User
import com.example.alarmproject.repository.UserRepository
import com.example.alarmproject.view.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

    val selectedPropertyTitle = MutableLiveData<VHAlarmProperty>()
    val profileData = MutableLiveData<User>()

    val auth = FirebaseAuth.getInstance()

    fun getMyProfileData(uid : String){
        viewModelScope.launch {
            repository.getMyProfile(uid).collect {
                profileData.postValue(it)
            }
        }
    }
}