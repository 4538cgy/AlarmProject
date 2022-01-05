package com.example.alarmproject.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivityMainBinding
import com.example.alarmproject.util.extension.repeatOnStarted
import com.example.alarmproject.view.base.BaseActivity
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("hello world")

        repeatOnStarted {
            viewModel.eventFlow.collect { eventHandle(it) }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivity) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigate(R.id.action_homeFragment_to_mainActivity)
    }

    private fun eventHandle(event: BaseViewModel.Event) = when (event) {
        is BaseViewModel.Event.TouchEvent -> {
            //todo
        }
        else -> {
        }
    }


}