package com.intech.soul_u.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel(
    val userId: String? = null,
    val profileImage: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val gender: String? = null,
    val birthDate: String? = null,
    val isPsychology: Boolean? = null
)