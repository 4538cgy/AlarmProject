package com.example.alarmproject.view.spalsh

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivitySplashBinding
import com.example.alarmproject.util.extension.doDelayed
import com.example.alarmproject.view.base.BaseActivity
import com.example.alarmproject.view.main.MainActivity
import com.example.alarmproject.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        doDelayed(2500) {
            finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
