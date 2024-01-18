package com.intech.soul_u.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intech.soul_u.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    private var binding : ActivityArticleBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
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