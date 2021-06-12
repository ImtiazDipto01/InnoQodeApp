package com.imtiaz.taskmanager.utils

import android.util.Patterns
import android.widget.EditText

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = this.length >= 6

fun String.isValidName(): Boolean = this.isNotEmpty()