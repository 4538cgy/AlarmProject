package com.example.alarmproject.util.firebase.storage

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseStorageModule {

    @Provides
    @Singleton
    fun provideDBInstance(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
}
