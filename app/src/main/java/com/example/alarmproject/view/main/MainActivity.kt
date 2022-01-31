package com.example.alarmproject.view.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.alarmproject.R
import com.example.alarmproject.databinding.ActivityMainBinding
import com.example.alarmproject.util.extension.repeatOnStarted
import com.example.alarmproject.util.receiver.AlarmReceiver
import com.example.alarmproject.util.receiver.AlarmReceiver.Companion.NOTIFICATION_ID
import com.example.alarmproject.util.system.Logger
import com.example.alarmproject.view.base.BaseActivity
import com.example.alarmproject.view.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.process(this.toString())
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        if (viewModel.auth.currentUser != null){
            viewModel.getMyProfileData(viewModel.auth.currentUser?.uid.toString())
        }
        return super.onCreateView(parent, name, context, attrs)
    }

    open fun setAlarm(){
    //open fun setAlarm(hour : Int , minute : Int , intervalMilliseconds : Long){릭

        println("알람 설정")
       alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent,  PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // Set the alarm to start at 8:30 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 2)
            set(Calendar.MINUTE, 33)
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000000,
            alarmIntent
        )
    }

    open fun alarmCancel(){
        alarmMgr?.cancel(alarmIntent)
    }
}