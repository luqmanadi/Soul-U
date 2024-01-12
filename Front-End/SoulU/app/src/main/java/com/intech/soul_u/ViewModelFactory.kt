package com.intech.soul_u

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intech.soul_u.data.Repository
import com.intech.soul_u.data.di.Injection
import com.intech.soul_u.ui.history.HistoryViewModel
import com.intech.soul_u.ui.home.HomeViewModel
import com.intech.soul_u.ui.login.LoginViewModel
import com.intech.soul_u.ui.profile.ProfileViewModel
import com.intech.soul_u.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            synchronized(this) {
                instance = Injection.provideRepository(context)?.let { ViewModelFactory(it) }
            }
            return instance as ViewModelFactory
        }

    }
}