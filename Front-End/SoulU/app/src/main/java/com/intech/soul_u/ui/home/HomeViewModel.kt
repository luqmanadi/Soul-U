package com.intech.soul_u.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intech.soul_u.data.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home Fragment"
    }
    val text: LiveData<String> = _text
}