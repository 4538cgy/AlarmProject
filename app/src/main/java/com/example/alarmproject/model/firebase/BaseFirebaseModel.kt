package com.example.alarmproject.model.firebase

import java.lang.Exception

data class BaseFirebaseModel (
    var message : String = "",
    var isSuccess : Boolean = false,
    var storageModel : StorageModel ?= null,
    var exception: Exception ?= null
){
    data class StorageModel(
        var downloadUrl : String ? = null
    )
}