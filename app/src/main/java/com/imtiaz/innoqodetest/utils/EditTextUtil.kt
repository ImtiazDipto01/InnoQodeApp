package com.imtiaz.innoqodetest.utils

import android.widget.EditText

fun EditText.text(): String = this.text.toString().trim()