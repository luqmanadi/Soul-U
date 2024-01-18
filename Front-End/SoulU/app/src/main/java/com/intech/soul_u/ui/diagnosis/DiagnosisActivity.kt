package com.intech.soul_u.ui.diagnosis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intech.soul_u.databinding.ActivityDiagnosisBinding

class DiagnosisActivity : AppCompatActivity() {

    private var binding: ActivityDiagnosisBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosisBinding.inflate(layoutInflater)
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