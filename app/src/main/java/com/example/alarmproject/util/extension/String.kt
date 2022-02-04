package com.example.alarmproject.util.extension

fun clearSpecialText(str : String) : String {
    val match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]"
    return str.replace(match, "")
}

fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}