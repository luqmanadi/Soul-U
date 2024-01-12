package com.intech.soul_u.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intech.soul_u.data.Repository

class ChatViewModel(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Inbox Page"
    }
    val text: LiveData<String> = _text
}