package com.imtiaz.innoqodetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.imtiaz.innoqodetest.databinding.ActivityMainBinding
import com.imtiaz.innoqodetest.ui.auth.AuthViewModel
import com.imtiaz.innoqodetest.utils.listeners.LoginSuccess

class MainActivity : AppCompatActivity(), LoginSuccess {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        setupNavGraph()
    }

    private fun setupNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.apply {
            val navGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)
            navGraph.startDestination =
                if (viewModel.isUserLoggedIn()) R.id.homeFragment else R.id.loginFragment

            navController.graph = navGraph
            setNavGraphWithBottomNavigation(this)
        }
    }

    private fun setNavGraphWithBottomNavigation(navHostFragment: NavHostFragment) {
        navHostFragment.apply {
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment,
                    R.id.userDetailsFragment -> showBottomNavigation()
                    else -> hideBottomNavigation()
                }
            }
        }
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigation.isVisible = false
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.isVisible = true
    }

    override fun onLoginSuccess() = setupNavGraph()
}