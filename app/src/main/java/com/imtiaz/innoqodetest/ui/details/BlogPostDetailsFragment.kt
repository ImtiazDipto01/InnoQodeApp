package com.imtiaz.innoqodetest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.imtiaz.innoqodetest.R
import com.imtiaz.innoqodetest.databinding.FragmentBlogPostDetailsBinding
import com.imtiaz.taskmanager.ui.base.BaseFragment


class BlogPostDetailsFragment : BaseFragment() {

    private var binding: FragmentBlogPostDetailsBinding? = null
    private val args: BlogPostDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlogPostDetailsBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        binding?.apply {
            setupToolBar(layoutAppbar.toolBar, "Details")

            webview.settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
            }
            webview.loadDataWithBaseURL(
                "",
                args.argBlogPost?.content ?: "",
                "text/html",
                "UTF-8",
                ""
            )

            val options: RequestOptions = RequestOptions()
                .transform(FitCenter())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .priority(Priority.HIGH)

            Glide.with(requireContext())
                .load(args.argBlogPost?.imageUrl)
                .apply(options)
                .into(ivPoster)
        }
    }
}