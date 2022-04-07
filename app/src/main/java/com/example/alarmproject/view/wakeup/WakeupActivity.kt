package com.example.alarmproject.view.wakeup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.alarmproject.R

class WakeupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wakeup)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        supportActionBar?.hide()
    }
}
