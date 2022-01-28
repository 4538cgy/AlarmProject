package com.example.alarmproject.model.firebase

import java.lang.Exception

data class BaseFirebaseModel (
    var message : String = "",
    var isSuccess : Boolean = false,
    var exception: Exception ?= null
)