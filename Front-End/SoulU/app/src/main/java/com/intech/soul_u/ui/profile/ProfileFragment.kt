package com.intech.soul_u.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.intech.soul_u.R
import com.intech.soul_u.data.UserModel
import com.intech.soul_u.databinding.FragmentProfileBinding
import com.intech.soul_u.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel>()

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

        val firebaseUser = auth.currentUser!!

        Log.d("Profile Fragment", firebaseUser.uid)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)

        Log.d("Profile Fragment", databaseReference.toString())


        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            return
        }

        setUpUI(Firebase.auth.currentUser!!)
        setUpAction()
    }

    private fun setUpUI(firebaseUser: FirebaseUser){
        binding.apply {

            databaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    if (user?.profileImage != null){
                        Glide.with(requireActivity())
                            .load(user.profileImage)
                            .circleCrop()
                            .into(photoProfile)
                    } else photoProfile.setImageResource(R.drawable.ic_launcher_background)

                    editTextName.text = if(user?.name != null) Editable.Factory.getInstance().newEditable(user.name.toString()) else Editable.Factory.getInstance().newEditable(getString(R.string.empty))
                    editTextEmail.text = if(firebaseUser.email != null) Editable.Factory.getInstance().newEditable(firebaseUser.email) else Editable.Factory.getInstance().newEditable(getString(R.string.empty))
                    editTextPhoneNumber.text = if(user?.phoneNumber != null) Editable.Factory.getInstance().newEditable(user.phoneNumber.toString()) else Editable.Factory.getInstance().newEditable(getString(R.string.empty))
                    editTextGender.text = if(user?.gender != null) Editable.Factory.getInstance().newEditable(user.gender.toString()) else Editable.Factory.getInstance().newEditable(getString(R.string.empty))
                    editTextBirthDate.text = if(user?.birthDate != null) Editable.Factory.getInstance().newEditable(user.birthDate.toString()) else Editable.Factory.getInstance().newEditable(getString(R.string.empty))
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireActivity(), error.message, Toast.LENGTH_SHORT).show()
                }

            })

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