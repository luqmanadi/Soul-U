package com.intech.soul_u.ui.meditation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intech.soul_u.databinding.ActivityMeditationBinding

class MeditationActivity : AppCompatActivity() {

    private var binding : ActivityMeditationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationBinding.inflate(layoutInflater)
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