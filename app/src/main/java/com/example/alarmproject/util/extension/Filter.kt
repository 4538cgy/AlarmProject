package com.example.alarmproject.util.extension

import android.text.InputFilter
import java.util.regex.Pattern

val filterKorEngNum = InputFilter { source, start, end, dest, dstart, dend ->

    val ps = Pattern.compile("^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9]*$")
    if (!ps.matcher(source).matches()){
        return@InputFilter ""
    }
    return@InputFilter null
}

