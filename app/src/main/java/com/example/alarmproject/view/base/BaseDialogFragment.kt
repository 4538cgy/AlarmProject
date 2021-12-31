package com.example.alarmproject.view.base

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel


abstract class BaseDialogFragment<B : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutId: Int) :
    DialogFragment() {
    lateinit var binding: B
    abstract val viewModel: VM
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@BaseDialogFragment
            setVariable(BR.vm, viewModel)
        }
        activity = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    open fun setCancel(setting: Boolean) {
        isCancelable = setting
    }

    open fun setWindowFeature(feature: Int) {
        activity.requestWindowFeature(feature)
    }

    open fun setBackground(drawable: ColorDrawable) {
        activity.window?.setBackgroundDrawable(drawable)
    }
}