package com.imtiaz.innoqodetest.ui.auth

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imtiaz.innoqodetest.R
import com.imtiaz.innoqodetest.databinding.FragmentSignUpBinding
import com.imtiaz.innoqodetest.ui.entity.LoginValidation
import com.imtiaz.innoqodetest.utils.enums.ActionStatus
import com.imtiaz.innoqodetest.utils.enums.ValidationErrTag
import com.imtiaz.innoqodetest.utils.text
import com.imtiaz.taskmanager.utils.Resource

class SignUpFragment(val onAction: (status: ActionStatus) -> Unit) : BottomSheetDialogFragment() {

    private var binding: FragmentSignUpBinding? = null
    private var isLoading = false
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setOnShowListener {
            Handler().post {
                val bottomSheet =
                    (dialog as? BottomSheetDialog)?.findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout
                bottomSheet?.let {
                    BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogNew)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeSignUp()
    }

    private fun initUI() {
        binding?.apply {
            btnSignup.setOnClickListener { handleSignUp() }
            ivClose.setOnClickListener { if (!isLoading) dismiss() }
        }
    }

    private fun handleSignUp() {
        binding?.apply {
            val validation = viewModel.isSignUpValidationSuccess(
                etEmail.text(), etPassword.text(), etFirstName.text(), etLastName.text()
            )

            if (validation.isValidate) {
                viewModel.processSignUp(
                    etEmail.text(),
                    etPassword.text(),
                    etFirstName.text(),
                    etLastName.text()
                )
            } else updateErrView(validation)
        }
    }

    private fun updateErrView(validation: LoginValidation) {
        binding?.apply {
            when (validation.errorView) {
                ValidationErrTag.FIRST_NAME -> etFirstName.error = validation.msg ?: ""
                ValidationErrTag.LAST_NAME -> etLastName.error = validation.msg ?: ""
                ValidationErrTag.EMAIL -> etEmail.error = validation.msg ?: ""
                ValidationErrTag.PASSWORD -> etPassword.error = validation.msg ?: ""
                else -> Unit
            }
        }
    }

    private fun observeSignUp() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            binding?.apply {
                when (it?.status) {
                    Resource.Status.LOADING -> loadingView(true)
                    Resource.Status.SUCCESS -> {
                        loadingView(false)
                        dismiss()
                        onAction(ActionStatus.SUCCESS)
                    }
                    Resource.Status.ERROR -> {
                        loadingView(false)
                        Toast.makeText(
                            requireContext(),
                            it.data?.exception ?: getString(R.string.err_something_went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun loadingView(isLoading: Boolean) {
        binding?.apply {
            pbSignupLoading.isVisible = isLoading
            this@SignUpFragment.isLoading = isLoading
        }
    }
}