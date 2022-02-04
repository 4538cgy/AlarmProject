package com.example.alarmproject.util.extension

import android.app.Activity
import android.widget.Toast

fun Activity.showToastLong(text : String){
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.showToastShort(text : String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}