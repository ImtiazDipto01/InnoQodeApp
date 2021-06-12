package com.imtiaz.innoqodetest.ui.auth

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.imtiaz.innoqodetest.ui.MainActivity
import com.imtiaz.innoqodetest.R
import com.imtiaz.innoqodetest.databinding.FragmentLoginBinding
import com.imtiaz.innoqodetest.ui.entity.LoginValidation
import com.imtiaz.innoqodetest.utils.enums.ActionStatus
import com.imtiaz.innoqodetest.utils.enums.ValidationErrTag
import com.imtiaz.innoqodetest.utils.listeners.LoginSuccess
import com.imtiaz.innoqodetest.utils.text
import com.imtiaz.taskmanager.ui.base.BaseFragment
import com.imtiaz.taskmanager.utils.Resource

class LoginFragment : BaseFragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: AuthViewModel by viewModels()

    private var loginSuccess: LoginSuccess? = null
    private var activity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
        loginSuccess = activity as LoginSuccess
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeLogin()
    }

    private fun initUI() {
        binding?.apply {
            btnLogin.setOnClickListener { handleLogin() }
            tvSignUp.setOnClickListener { openSignupPopup() }
        }
    }

    private fun openSignupPopup() {
        requireActivity().supportFragmentManager.let {
            SignUpFragment(actionSignUp()).apply {
                show(it, "SignUpFragment")
            }
        }
    }

    private fun handleLogin() {
        binding?.apply {
            val validation = viewModel.isLoginValidSuccess(
                etEmail.text(),
                etPassword.text()
            )

            if (validation.isValidate) {
                viewModel.processLogin(etEmail.text(), etPassword.text())
            } else updateErrView(validation)
        }
    }

    private fun updateErrView(validation: LoginValidation) {
        binding?.apply {
            when (validation.errorView) {
                ValidationErrTag.EMAIL -> etEmail.error = validation.msg ?: ""
                ValidationErrTag.PASSWORD -> etPassword.error = validation.msg ?: ""
                else -> Unit
            }
        }
    }

    private fun observeLogin() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Resource.Status.LOADING -> showLoader()
                Resource.Status.SUCCESS -> {
                    dismissLoader()
                    toast("Success")
                    loginSuccess?.onLoginSuccess()
                }
                Resource.Status.ERROR -> {
                    dismissLoader()
                    errorAlert(
                        msg = it.data?.exception ?: getString(R.string.err_something_went_wrong)
                    )
                }
            }
        })
    }

    private fun actionSignUp(): (ActionStatus) -> Unit {
        return { status ->
            when (status) {
                ActionStatus.SUCCESS -> {
                    loginSuccess?.onLoginSuccess()
                }
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginSuccess = null
    }
}