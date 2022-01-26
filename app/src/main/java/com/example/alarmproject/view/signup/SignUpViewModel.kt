package com.example.alarmproject.view.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.R
import com.example.alarmproject.util.extension.clearSpecialText
import com.example.alarmproject.util.flow.MutableEventFlow
import com.example.alarmproject.util.flow.asEventFlow
import com.example.alarmproject.view.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "MainViewModel"

    var isComplete = MutableLiveData<Boolean>()

    val currentNickName = MutableLiveData<String>()
    val currentGender = MutableLiveData<String>()

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        currentNickName.value = clearSpecialText(s.toString())
    }
}
