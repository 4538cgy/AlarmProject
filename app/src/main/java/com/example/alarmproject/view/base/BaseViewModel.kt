package com.example.alarmproject.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.util.flow.MutableEventFlow
import com.example.alarmproject.util.flow.asEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    open fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class TouchEvent(val value: Any) : Event()
        data class ResponseCommonData<ITEM>(val value: ITEM) : Event()
    }
}