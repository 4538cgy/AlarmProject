package com.example.alarmproject.util.module

import com.example.alarmproject.util.firebase.auth.AuthRepository
import com.example.alarmproject.util.firebase.auth.FirebaseAuthenticator
import com.example.alarmproject.view.base.BaseAuthRepository
import com.example.alarmproject.view.base.BaseAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseAppModule {

    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideRepository(): BaseAuthRepository {
        return AuthRepository(provideAuthenticator())
    }
}