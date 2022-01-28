package com.example.alarmproject.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.util.flow.MutableEventFlow
import com.example.alarmproject.util.flow.asEventFlow
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    var disposable = CompositeDisposable()

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
        data class ErrorCode(val code: Int) : Result<Nothing>()
        data class Message(val message: String) : Result<String>()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}