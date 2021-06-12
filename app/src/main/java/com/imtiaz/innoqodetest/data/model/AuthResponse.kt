package com.imtiaz.innoqodetest.data.model

import com.google.firebase.auth.FirebaseUser

data class AuthResponse(
    val user: FirebaseUser? = null,
    val exception: String? = null
)