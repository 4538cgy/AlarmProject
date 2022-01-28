package com.example.alarmproject.view.alarmedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.alarmproject.R
import com.example.alarmproject.databinding.FragmentAlarmEditBinding
import com.example.alarmproject.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmEditFragment : BaseFragment<FragmentAlarmEditBinding,AlarmEditViewModel>(R.layout.fragment_alarm_edit) {

    override val viewModel: AlarmEditViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun goEditProperty(){
        Navigation.findNavController(binding.root).navigate(R.id.action_alarmEditFragment_to_alarmEditPropertyFragment)
    }

}