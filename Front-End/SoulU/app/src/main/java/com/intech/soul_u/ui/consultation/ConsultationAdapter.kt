package com.intech.soul_u.ui.consultation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intech.soul_u.R
import com.intech.soul_u.data.UserModel
import com.intech.soul_u.databinding.CardProfilePsikologBinding


class ConsultationAdapter(private val context: Context, private val userList: ArrayList<UserModel>):
    RecyclerView.Adapter<ConsultationAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: CardProfilePsikologBinding): RecyclerView.ViewHolder(binding.root){
        val name = binding.titleNamePsychology
        val image = binding.imageProfilePsychology
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardProfilePsikologBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList[position]

        holder.name.text = user.name ?: "Dr. Serizawa"
        Glide.with(context)
            .load(user.profileImage)
            .placeholder(R.drawable.photo_example)
            .into(holder.image)

    }
}