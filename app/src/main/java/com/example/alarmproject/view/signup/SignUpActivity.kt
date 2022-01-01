package com.example.alarmproject.view.signup

import android.os.Bundle
import androidx.activity.viewModels
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivitySignUpBinding
import com.example.alarmproject.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {

    override val viewModel: SignUpViewModel by viewModels()

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
}