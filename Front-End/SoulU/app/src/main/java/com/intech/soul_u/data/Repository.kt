package com.intech.soul_u.data

import com.intech.soul_u.data.pref.UserPreference
import com.intech.soul_u.data.remote.retrofit.ApiService

class Repository(apiService: ApiService, pref: UserPreference) {

    companion object {

        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): Repository? {
            synchronized(this) {
                instance = Repository(apiService, userPreference)
            }
            return instance
        }

    }
}