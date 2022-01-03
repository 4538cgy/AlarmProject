package com.example.alarmproject.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes
    private val layoutId: Int,
) : AppCompatActivity() {

    abstract val viewModel: VM
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, viewModel)
        }
    }
}