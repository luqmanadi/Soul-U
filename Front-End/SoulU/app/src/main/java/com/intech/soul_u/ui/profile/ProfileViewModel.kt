package com.intech.soul_u.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intech.soul_u.data.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Profile Page"
    }
    val text: LiveData<String> = _text
}