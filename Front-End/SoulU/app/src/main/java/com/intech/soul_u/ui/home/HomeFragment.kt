package com.intech.soul_u.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intech.soul_u.databinding.FragmentHomeBinding
import com.intech.soul_u.ui.article.ArticleActivity
import com.intech.soul_u.ui.consultation.ConsultationActivity
import com.intech.soul_u.ui.diagnosis.DiagnosisActivity
import com.intech.soul_u.ui.meditation.MeditationActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
    }

    private fun setUpAction(){
        binding.apply {
            layoutArticle.setOnClickListener {
                startActivity(Intent(requireActivity(), ArticleActivity::class.java))
            }

            layoutMeditation.setOnClickListener {
                startActivity(Intent(requireActivity(), MeditationActivity::class.java))
            }

            layoutDiagnose.setOnClickListener {
                startActivity(Intent(requireActivity(), DiagnosisActivity::class.java))
            }

            layoutConsultation.setOnClickListener {
                startActivity(Intent(requireActivity(), ConsultationActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}