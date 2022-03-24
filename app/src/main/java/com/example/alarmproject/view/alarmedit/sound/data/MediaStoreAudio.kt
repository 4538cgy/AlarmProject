package com.example.alarmproject.view.alarmedit.sound.data

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class MediaStoreAudio(
    val id:Long,
    val displayName:String,
    val dateAdded: Date,
    val contentUri: Uri
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreAudio>()
        {
            override fun areItemsTheSame(
                oldItem: MediaStoreAudio,
                newItem: MediaStoreAudio
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: MediaStoreAudio,
                newItem: MediaStoreAudio
            ): Boolean = oldItem == newItem

        }
    }
}