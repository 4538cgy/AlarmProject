package com.example.alarmproject.view.signup

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivitySignUpBinding
import com.example.alarmproject.util.extension.filterKorEngNum
import com.example.alarmproject.util.extension.isNotNullOrEmpty
import com.example.alarmproject.util.extension.showToastLong
import com.example.alarmproject.view.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {

    override val viewModel: SignUpViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()
    private lateinit var getAuthResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        initRegisterActivityResult()

        binding.etNickname.apply {
            filters = arrayOf(filterKorEngNum, InputFilter.LengthFilter(8))
        }
        Glide.with(binding.root.context).load(R.drawable.ic_launcher_foreground).apply(RequestOptions().circleCrop()).into(binding.ivProfile)

        binding.rgGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbWoman.id -> {
                    setGender("여자")
                }
                binding.rbMan.id -> {
                    setGender("남자")
                }
            }
        }
        binding.tvGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun setGender(gender: String) {
        viewModel.currentGender.value = gender
    }

    private fun initRegisterActivityResult() {
        getAuthResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                firebaseAuthWithGoogle(task.result.idToken.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user ?: return@addOnCompleteListener)
            } else {
                updateUI(null)
            }
        }
    }

    fun signIn() {
        if (viewModel.currentGender.value.isNotNullOrEmpty() && viewModel.currentNickName.value.isNotNullOrEmpty()) {
            val signInIntent = googleSignInClient.signInIntent
            getAuthResult.launch(signInIntent)
        }else{
            showToastLong("SNS 계정 연결 전 닉네임과 성별을 입력해주세요.")
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        user ?: return
        viewModel.isComplete.value = true
    }
}
