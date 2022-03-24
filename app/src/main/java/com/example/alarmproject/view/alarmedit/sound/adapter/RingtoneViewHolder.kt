package com.example.alarmproject.view.alarmedit.sound.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmproject.databinding.ItemRingtoneBinding
import com.example.alarmproject.view.alarmedit.sound.data.MediaStoreAudio

class RingtoneViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val bnd: ItemRingtoneBinding? = DataBindingUtil.bind(view)

    fun render(item: MediaStoreAudio, onRingtoneClick: RingtoneAdapter.OnRingtoneClick?) {
        bnd?.ringtoneText?.text = item.displayName

        bnd?.ringtoneText?.setOnClickListener {
            onRingtoneClick?.onRingtoneClick(item)
        }
    }
}
