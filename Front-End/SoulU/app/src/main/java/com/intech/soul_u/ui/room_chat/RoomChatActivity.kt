package com.intech.soul_u.ui.room_chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intech.soul_u.databinding.ActivityRoomChatBinding

class RoomChatActivity : AppCompatActivity() {

    private var binding : ActivityRoomChatBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomChatBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpAction()
    }

    private fun setUpAction(){
        binding?.apply {
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}