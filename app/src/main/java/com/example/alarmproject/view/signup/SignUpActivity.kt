package com.example.alarmproject.view.signup

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivitySignUpBinding
import com.example.alarmproject.model.user.User
import com.example.alarmproject.util.extension.filterKorEngNum
import com.example.alarmproject.util.extension.isNotNullOrEmpty
import com.example.alarmproject.util.extension.repeatOnStarted
import com.example.alarmproject.util.extension.showToastLong
import com.example.alarmproject.view.base.BaseActivity
import com.example.alarmproject.view.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {

    override val viewModel: SignUpViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()

    private val authResultCallback = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            firebaseAuthWithGoogle(task.result.idToken.toString())
        }
    }

    private val profileImageCallback = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Glide.with(binding.root.context).load(it.data?.data).circleCrop().into(binding.ivProfile)

        viewModel.profileImageUri.postValue(it.data?.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        initFlowHanlder()

        viewModel.isContainUser.observe(this) {
            println("테스트 유저가 존재하나요? $it")
        }

        viewModel.profileImageUri.observe(this){
            if (it.toString().isNotNullOrEmpty()) binding.ivProfileAdd.visibility = View.INVISIBLE
        }

        binding.etNickname.apply {
            filters = arrayOf(filterKorEngNum, InputFilter.LengthFilter(8))
        }

        binding.rgGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbWoman.id -> {
                    setGender(GENDER_WOMAN)
                }
                binding.rbMan.id -> {
                    setGender(GENDER_MAN)
                }
            }
        }
        binding.ivProfile.setOnClickListener { openGallery() }
        binding.tvGoogle.setOnClickListener { signIn() }
        binding.btnComplete.setOnClickListener {
            initFlowHanlder()
            uploadUserData()
        }
    }

    private fun setGender(gender: String) {
        viewModel.currentGender.value = gender
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
        if (viewModel.currentGender.value.isNotNullOrEmpty() && viewModel.currentNickName.value.isNotNullOrEmpty() && viewModel.profileImageUri.value.toString().isNotNullOrEmpty()) {
            val signInIntent = googleSignInClient.signInIntent
            authResultCallback.launch(signInIntent)
        } else {
            showToastLong(ALERT_INPUT_ALL)
        }
    }

    private fun uploadUserData() {
        viewModel.setUserInfo(getCurrentUserData())
    }

    private fun initFlowHanlder() {
        repeatOnStarted {
            viewModel.userRequestFlow.collect {
                when (it) {
                    is BaseViewModel.Result.Success -> {
                        println("${it.data.message}")
                    }
                    is BaseViewModel.Result.Error -> {
                        println("${it.exception}")
                    }
                }
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        user ?: return
        viewModel.isComplete.value = true
        //현재 UID 가 DB에 존재하는지 체크
        viewModel.isContainUser(auth.currentUser?.uid.toString())
    }

    private fun getCurrentUserData(): User {
        val user = User()
        user.apply {
            email = auth.currentUser?.email.toString()
            gender = viewModel.currentGender.value.toString()
            name = viewModel.currentNickName.value.toString()
            timestamp = System.currentTimeMillis()
            uid = auth.currentUser?.uid.toString()
        }
        return user
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        profileImageCallback.launch(intent)
    }

    companion object {
        const val GENDER_WOMAN = "여성"
        const val GENDER_MAN = "남성"
        const val ALERT_INPUT_ALL = "SNS 계정 연결 전 프로필 이미지,닉네임,성별을 입력해주세요."
    }
}
