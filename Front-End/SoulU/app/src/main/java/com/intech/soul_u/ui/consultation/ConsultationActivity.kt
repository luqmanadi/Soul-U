package com.intech.soul_u.ui.consultation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.intech.soul_u.data.UserModel
import com.intech.soul_u.databinding.ActivityConsultationBinding

class ConsultationActivity : AppCompatActivity() {

    private var userList = ArrayList<UserModel>()

    private var binding : ActivityConsultationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getPsychologyList()
        setUpAction()
//        setUpList()
    }

//    private fun setUpList(){
//        binding?.apply {
//            val consultationAdapter = ConsultationAdapter(this@ConsultationActivity, userList)
//            rvListPsychology.layoutManager = LinearLayoutManager(this@ConsultationActivity)
//            rvListPsychology.adapter = consultationAdapter
//        }
//
//    }

    private fun getPsychologyList(){
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (dataSnapshot: DataSnapshot in snapshot.children){
                    val user = dataSnapshot.getValue(UserModel::class.java)

                    if (!user!!.userId.equals(firebase.uid)){
                        userList.add(user)
                    }
                }
                binding?.apply {
                    val consultationAdapter = ConsultationAdapter(this@ConsultationActivity, userList)
                    rvListPsychology.layoutManager = LinearLayoutManager(this@ConsultationActivity)
                    rvListPsychology.adapter = consultationAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ConsultationActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
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