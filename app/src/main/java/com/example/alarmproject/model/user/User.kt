package com.example.alarmproject.model.user

data class User(
    var uid: String? = null,
    var profileImageUrl: String? = null,
    var name: String? = null,
    var gender: String? = null,
    var email: String? = null,
    var timestamp: Long? = null
)