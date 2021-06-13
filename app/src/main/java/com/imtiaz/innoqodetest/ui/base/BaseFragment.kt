package com.imtiaz.taskmanager.ui.base

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.imtiaz.innoqodetest.R

open class BaseFragment : Fragment() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun errorAlert(title: String = "Opps!", msg: String, cancelAction: () -> Unit = {}) {
        setupBasicAlert(title, msg)
            .setPositiveButton(resources.getString(R.string.cancle)) { dialog, _ ->
                cancelAction()
                dialog.dismiss()
            }
            .show()
    }

    protected fun alert(title: String = "Message", msg: String, positiveAction: () -> Unit = {}) {
        setupBasicAlert(title, msg)
            .setPositiveButton(resources.getString(R.string.confirm)) { dialog, _ ->
                positiveAction()
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cancle)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupBasicAlert(title: String, msg: String): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(msg)
    }

    protected fun navigateTo(
        direction: NavDirections? = null,
    ) {
        if(direction != null) {
            findNavController().navigate(direction)
        }
    }

    fun showLoader() {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Please wait while loading...")
        progressDialog.show()
    }

    fun dismissLoader() {
        try {
            if (this::progressDialog.isInitialized && progressDialog.isShowing)
                progressDialog.dismiss()

        } catch (exp: Exception) {
        }
    }

    fun setupToolBar(toolbar: MaterialToolbar, title: String = "", colorId: Int = R.color.black) {
        toolbar.apply {
            setTitle(title)
            setTitleTextColor(ContextCompat.getColor(requireContext(), colorId))
            setNavigationIconTint(ContextCompat.getColor(requireContext(), colorId))
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}