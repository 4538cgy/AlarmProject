package com.example.alarmproject.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.util.flow.MutableEventFlow
import com.example.alarmproject.util.flow.asEventFlow
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _dataFlow = MutableEventFlow<Data>()
    val dataFlow = _dataFlow.asEventFlow()

    val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()

    var disposable = CompositeDisposable()

    open fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    open fun data(data: Data) {
        viewModelScope.launch {
            _dataFlow.emit(data)
        }
    }

    sealed class Event {
        data class TouchEvent(val value: Any) : Event()
        data class ResponseCommonData<ITEM>(val value: ITEM) : Event()
    }

    sealed class Data {
        data class Error(val errorMessage: String) : Data()
        data class Success(val data: Any) : Data()
        data class Loading(val startPoint: String) : Data()
    }

    sealed class AllEvents {
        data class Message(val message: String) : AllEvents()
        data class ErrorCode(val code: Int) : AllEvents()
        data class Error(val error: String) : AllEvents()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}