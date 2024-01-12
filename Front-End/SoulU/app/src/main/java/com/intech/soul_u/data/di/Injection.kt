package com.intech.soul_u.data.di

import android.content.Context
import com.intech.soul_u.data.Repository
import com.intech.soul_u.data.pref.UserPreference
import com.intech.soul_u.data.pref.dataStore
import com.intech.soul_u.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): Repository? {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return Repository.getInstance(apiService, pref)
    }
}