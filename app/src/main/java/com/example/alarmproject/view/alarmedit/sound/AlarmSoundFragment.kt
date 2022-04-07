package com.example.alarmproject.view.alarmedit.sound


import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmproject.R
import com.example.alarmproject.databinding.FragmentAlarmSoundBinding
import com.example.alarmproject.view.alarmedit.sound.adapter.RingtoneAdapter
import com.example.alarmproject.view.alarmedit.sound.data.MediaStoreAudio
import com.example.alarmproject.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmSoundFragment :
    BaseFragment<FragmentAlarmSoundBinding, AlarmSoundViewModel>(R.layout.fragment_alarm_sound),
    RingtoneAdapter.OnRingtoneClick{

    override val viewModel: AlarmSoundViewModel by activityViewModels()

    private val PERMISSION_REQUEST_CODE = 1001

    private lateinit var ringtoneAdapter: RingtoneAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        binding.soundBack.setOnClickListener {
            goBack()
        }
        binding.soundAdd.setOnClickListener {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                }
            }
        }
    }


    private fun initView() {
        val permissionGrant = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionGrant == PackageManager.PERMISSION_GRANTED) {
            viewModel.getAddSoundList()
        } else {
            requestPermission()
        }



        viewModel.ringtoneData.observe(requireActivity()) {
            ringtoneAdapter = RingtoneAdapter(it)
            binding.soundDefaultList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.soundDefaultList.adapter = ringtoneAdapter
        }
    }

    private fun requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s",
                        requireContext().applicationContext.packageName
                    )
                )
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }



    private fun goBack() {
        if(viewModel.selectedSound.isNotEmpty()) {

        }

        Navigation.findNavController(binding.root).navigate(R.id.action_alaramSoundFragment_to_alaramEditFragment)
    }

    override fun onRingtoneClick(item: MediaStoreAudio?) {
        item?.run {

        }
    }

}