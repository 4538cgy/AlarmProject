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
import kotlinx.coroutines.flow.first
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

    fun uploadUserInfo(data: User) {
        viewModelScope.launch {
            //사진 업로드하고 다운로드 url 나오면
            val responseUploadImage = repository.uploadProfileImage(data.uid.toString(), Uri.parse(profileImageUri.value.toString()))
            data.profileImageUrl = responseUploadImage.storageModel?.downloadUrl
            uploadUserData(data)
        }
    }

    suspend fun uploadUserData(data: User) {
        //회원 정보 업로드
        val responseUploadUserData = repository.setUser(data)
        if (responseUploadUserData.isSuccess) {
            userRequestFlow.emit(Result.Success(responseUploadUserData))
        } else {
            userRequestFlow.emit(Result.Error(responseUploadUserData.exception ?: return))
        }
    }

    fun isContainUser(uid: String) {
        viewModelScope.launch {
            isContainUser.postValue(repository.isExistsUser(uid))
        }
    }
}
