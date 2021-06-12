package com.imtiaz.innoqodetest.ui.entity

import com.imtiaz.innoqodetest.utils.enums.ValidationErrTag

data class LoginValidation(
    val isValidate: Boolean,
    val msg: String? = null,
    val errorView: ValidationErrTag = ValidationErrTag.NOTHING
)
