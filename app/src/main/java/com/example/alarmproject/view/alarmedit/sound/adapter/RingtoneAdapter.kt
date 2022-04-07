package com.example.alarmproject.view.alarmedit.sound.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmproject.R
import com.example.alarmproject.view.alarmedit.sound.data.MediaStoreAudio

class RingtoneAdapter(private var itemList: List<MediaStoreAudio>): RecyclerView.Adapter<RingtoneViewHolder>() {

    private var onRingtoneClick: OnRingtoneClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RingtoneViewHolder {
        return RingtoneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ringtone, parent, false))
    }

    override fun onBindViewHolder(holder: RingtoneViewHolder, position: Int) {
        holder.render(itemList[position], onRingtoneClick)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setRingtoneList(list: List<MediaStoreAudio>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    fun setRingtoneClick(onRingtoneClick: OnRingtoneClick) {
        this.onRingtoneClick = onRingtoneClick
    }

    interface OnRingtoneClick {
        fun onRingtoneClick(item: MediaStoreAudio?)
    }
}