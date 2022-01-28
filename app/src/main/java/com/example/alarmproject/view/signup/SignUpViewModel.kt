package com.example.alarmproject.view.signup

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.model.firebase.BaseFirebaseModel
import com.example.alarmproject.model.user.User
import com.example.alarmproject.repository.UserRepository
import com.example.alarmproject.util.extension.clearSpecialText
import com.example.alarmproject.util.flow.MutableEventFlow
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

    private val TAG = "MainViewModel"

    var isComplete = MutableLiveData<Boolean>()

    val currentNickName = MutableLiveData<String>()
    val currentGender = MutableLiveData<String>()

    val userRequestFlow = MutableEventFlow<Result<BaseFirebaseModel>>()

    val isContainUser = MutableLiveData<Boolean>()

    val profileImageUri = MutableLiveData<Uri>()

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        currentNickName.value = clearSpecialText(s.toString())
    }

    fun setUserInfo(data : User){
        viewModelScope.launch {
            val response = repository.setUser(data)
            if (response.isSuccess){
                userRequestFlow.emit(Result.Success(response))
            }else{
                userRequestFlow.emit(Result.Error(response.exception?:return@launch))
            }
        }
    }

    fun isContainUser(uid : String){
        viewModelScope.launch {
            repository.isExistsUser(uid).collect {
                println("유저 체크 $it")
                isContainUser.postValue(it)
            }
        }
    }
}
