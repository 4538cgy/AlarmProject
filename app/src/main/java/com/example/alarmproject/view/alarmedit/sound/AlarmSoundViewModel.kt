package com.example.alarmproject.view.alarmedit.sound

import android.app.Application
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmproject.view.alarmedit.sound.data.MediaStoreAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AlarmSoundViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    var selectedSound: String = ""

    private val _ringtoneData = MutableLiveData<List<MediaStoreAudio>>()
    val ringtoneData: LiveData<List<MediaStoreAudio>>
        get() = _ringtoneData


    fun getAddSoundList() {
        viewModelScope.launch {
            val data = queryAudio()
            _ringtoneData.postValue(data)
        }
    }


    private suspend fun queryAudio(): List<MediaStoreAudio> {
        val audios = mutableListOf<MediaStoreAudio>()

        withContext(Dispatchers.IO) {
            val projection =
                arrayOf(
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DATE_ADDED,
                    MediaStore.Audio.Media._ID,
                )


            val selection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                "${MediaStore.Audio.Media.DATA} LIKE ?"
            } else {
                null
            }

            val selectionArgs = if (selection != null) {
                arrayOf("%Solaroid%")
            } else {
                null
            }

            val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

            val query = getApplication<Application>().contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val dateModifiedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val dateModified =
                        Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
                    val displayName = cursor.getString(nameColumn)

                    val contentUri =
                        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                    val audio = MediaStoreAudio(id, displayName, dateModified, contentUri)
                    audios += audio
                }
            }


        }

        Log.d("Sound", "audios : {${audios}}")
        return audios
    }


}