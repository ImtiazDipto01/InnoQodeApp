package com.imtiaz.innoqodetest.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.imtiaz.innoqodetest.App
import com.imtiaz.innoqodetest.R
import com.imtiaz.innoqodetest.data.repository.AuthRepository
import com.imtiaz.innoqodetest.data.remote.entity.AuthResponse
import com.imtiaz.innoqodetest.ui.entity.LoginValidation
import com.imtiaz.innoqodetest.utils.enums.ValidationErrTag
import com.imtiaz.taskmanager.utils.Resource
import com.imtiaz.taskmanager.utils.isValidEmail
import com.imtiaz.taskmanager.utils.isValidName
import com.imtiaz.taskmanager.utils.isValidPassword

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val repository: AuthRepository by lazy { AuthRepository(auth) }

    val liveData: MutableLiveData<Resource<AuthResponse?>?> by lazy { MutableLiveData() }

    fun processLogin(email: String, password: String) =
        repository.processLogin(liveData, email, password)

    fun processSignUp(email: String, password: String, firstName: String, lastName: String) =
        repository.processSignUp(liveData, email, password, firstName, lastName)

    fun isLoginValidSuccess(email: String, password: String): LoginValidation {
        return when {
            !email.isValidEmail() -> LoginValidation(
                false,
                App.getContext().getString(R.string.err_email),
                ValidationErrTag.EMAIL
            )
            !password.isValidPassword() -> LoginValidation(
                false,
                App.getContext().getString(R.string.err_password),
                ValidationErrTag.PASSWORD
            )
            else -> LoginValidation(true)
        }
    }

    fun isSignUpValidationSuccess(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): LoginValidation {

        return when {
            !firstName.isValidName() -> LoginValidation(
                false,
                App.getContext().getString(R.string.err_name),
                ValidationErrTag.FIRST_NAME
            )

            !lastName.isValidName() -> LoginValidation(
                false,
                App.getContext().getString(R.string.err_name),
                ValidationErrTag.LAST_NAME
            )

            !email.isValidEmail() -> LoginValidation(
                false,
                App.getContext().getString(R.string.err_email),
                ValidationErrTag.EMAIL
            )

            !password.isValidPassword() -> LoginValidation(
                false,
                App.getContext().getString(R.string.err_password),
                ValidationErrTag.PASSWORD
            )
            else -> LoginValidation(true)
        }
    }

    fun isUserLoggedIn() : Boolean = auth.currentUser != null
}