package com.example.alarmproject.view.signup

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivitySignUpBinding
import com.example.alarmproject.util.extension.repeatOnStarted
import com.example.alarmproject.view.base.BaseActivity
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {

    override val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerObservers()
        listenToChannels()

        Glide.with(binding.root.context)
            .load(R.drawable.ic_launcher_foreground)
            .apply(RequestOptions().circleCrop())
            .into(binding.ivProfile)
    }

    fun onCreateUser(view: View) {
        viewModel.signUpUser("test@test.com", "qwer1234", "qwer1234")
    }

    private fun registerObservers() {
        /*
        viewModel.currentUser.observe(viewLifecycleOwner, { user ->
            user?.let {
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
            }
        })
         */
    }

    private fun listenToChannels() {
        repeatOnStarted {
            viewModel.allEventsFlow.collect { event ->
                when (event) {
                    is BaseViewModel.AllEvents.Error -> {
                        Toast.makeText(binding.root.context, event.error, Toast.LENGTH_SHORT).show()

                    }
                    is BaseViewModel.AllEvents.Message -> {
                        Toast.makeText(binding.root.context, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is BaseViewModel.AllEvents.ErrorCode -> {
                        if (event.code == 1) {
                            //email should not be empty
                        }
                        if (event.code == 2) {
                            //passowrd should not be empty
                        }
                        if (event.code == 3) {
                            //passwords do not match
                        }
                    }

                    else -> {
                        Log.d(TAG, "listenToChannels: No event received so far")
                    }
                }
            }
        }
    }


}
