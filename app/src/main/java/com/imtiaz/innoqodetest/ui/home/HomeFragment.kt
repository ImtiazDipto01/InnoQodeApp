package com.imtiaz.innoqodetest.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.imtiaz.innoqodetest.R
import com.imtiaz.innoqodetest.data.local.entity.BlogPost
import com.imtiaz.innoqodetest.databinding.FragmentHomeBinding
import com.imtiaz.taskmanager.ui.base.BaseFragment
import com.imtiaz.taskmanager.utils.Resource

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeAdapter: HomeAdapter
    private var list = mutableListOf<BlogPost>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observePosts()
    }

    private fun setupAdapter() {
        binding?.apply {
            rvPosts.apply {
                homeAdapter = HomeAdapter(list) {
                    navigateTo(HomeFragmentDirections.actionHomeFragmentToBlogPostDetailsFragment(it))
                }
                adapter = homeAdapter
            }
        }
    }

    private fun observePosts() {
        viewModel.postsResponse.observe(viewLifecycleOwner, Observer {
            binding?.apply {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        updateLoadingView(true)
                    }
                    Resource.Status.SUCCESS -> {
                        updateLoadingView(false)
                        list.apply {
                            clear()
                            addAll(it.data ?: listOf())
                            homeAdapter.notifyDataSetChanged()
                            binding?.rvPosts?.isVisible = true
                        }
                    }
                    Resource.Status.ERROR -> {
                        updateLoadingView(false)
                    }
                }
            }
        })
    }

    private fun updateLoadingView(isLoading: Boolean) = binding?.apply {
        layoutLoading.homeShimmer.apply {
            isVisible = if (isLoading) {
                startShimmer()
                true
            } else {
                stopShimmer()
                false
            }
        }
    }

}