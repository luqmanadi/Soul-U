package com.intech.soul_u.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.intech.soul_u.R
import com.intech.soul_u.ViewModelFactory
import com.intech.soul_u.databinding.FragmentProfileBinding
import com.intech.soul_u.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            return
        }

        setUpUI(firebaseUser)
        setUpAction()
    }

    private fun setUpUI(firebaseUser: FirebaseUser){
        binding.apply {
            Glide.with(requireActivity())
                .load(firebaseUser.photoUrl)
                .circleCrop()
                .into(photoProfile)
            editTextName.text = Editable.Factory.getInstance().newEditable(firebaseUser.displayName)
            editTextEmail.text = Editable.Factory.getInstance().newEditable(firebaseUser.email)
        }
    }

    private fun setUpAction(){

        binding.buttonLogout.setOnClickListener {
            signOut()
        }
    }


    private fun signOut(){
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.title_logout_account))
            .setMessage(getString(R.string.description_logout))
            .setPositiveButton(getString(R.string.yes)){_,_ ->
                auth.signOut()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(getString(R.string.no)){_,_ ->}
            .create()
            .show()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}