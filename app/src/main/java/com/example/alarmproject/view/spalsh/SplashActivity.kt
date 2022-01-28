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
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModels()

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        doDelayed(2500) {
            finish()
            println("테스트 로그인되어있음? ${isLogin()}")
            if (isLogin()) {
                println("테스트 이미 로그인 되어 있음 Main 으로 이동")
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                println("테스트 로그인이 되어 있지 않은 signUp으로 이동")
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }

    }

    private fun isLogin() = auth.currentUser != null

}
