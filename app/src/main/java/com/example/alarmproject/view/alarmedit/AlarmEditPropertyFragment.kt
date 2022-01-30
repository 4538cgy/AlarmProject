package com.example.alarmproject.view.alarmedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.alarmproject.R
import com.example.alarmproject.databinding.FragmentAlarmEditPropertyBinding
import com.example.alarmproject.view.base.BaseFragment
import com.example.alarmproject.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmEditPropertyFragment : BaseFragment<FragmentAlarmEditPropertyBinding,AlarmEditPropertyViewModel>(R.layout.fragment_alarm_edit_property) {

    override val viewModel: AlarmEditPropertyViewModel by viewModels()
    private val activityViewModels : MainViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        binding.ibBack.setOnClickListener {
            goBack()
        }
    }

    private fun initView(){
        binding.tvTitle.text = activityViewModels.selectedPropertyTitle.value?.title.toString()
    }

    private fun goBack(){
        Navigation.findNavController(binding.root).navigate(R.id.action_alarmEditPropertyFragment_to_alarmEditFragment)
    }
}