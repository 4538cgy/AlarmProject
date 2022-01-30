package com.example.alarmproject.view.alarmedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.alarmproject.BR
import com.example.alarmproject.R
import com.example.alarmproject.databinding.FragmentAlarmEditPropertyBinding
import com.example.alarmproject.databinding.VhAlarmPropertyBinding
import com.example.alarmproject.model.alarm.VHAlarmProperty
import com.example.alarmproject.view.base.BaseFragment
import com.example.alarmproject.view.base.BaseRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmEditPropertyFragment : BaseFragment<FragmentAlarmEditPropertyBinding,AlarmEditPropertyViewModel>(R.layout.fragment_alarm_edit_property) {

    override val viewModel: AlarmEditPropertyViewModel by viewModels()

    private val propertyAdapter = BaseRecyclerView.Adapter<VHAlarmProperty, VhAlarmPropertyBinding>(
        layoutResId = R.layout.vh_alarm_property,
        bindingVariableId = BR.data,
        callBack = { }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_edit_property, container, false)
    }

}