package com.example.alarmproject.view.spalsh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivitySplashBinding
import com.example.alarmproject.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding,SplashViewModel>(R.layout.activity_splash) {

    override val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("hello splash")
    }
}