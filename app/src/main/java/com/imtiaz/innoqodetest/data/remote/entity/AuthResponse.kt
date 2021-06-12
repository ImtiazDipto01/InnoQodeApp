package com.imtiaz.innoqodetest.data.remote.entity

import com.google.firebase.auth.FirebaseUser

data class AuthResponse(
    val user: FirebaseUser? = null,
    val exception: String? = null
)