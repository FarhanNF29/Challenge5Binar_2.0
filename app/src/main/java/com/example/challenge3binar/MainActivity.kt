package com.example.challenge3binar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.challenge3binar.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)

        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.feedFragment, R.id.officialStoreFragment, R.id.wishListFragment, R.id.transaksiFragment))

        //setupActionBarWithNavController(navController, appBarConfiguration)

        binding.botNav.setupWithNavController(navController)

        val bottomNavigationView = binding.botNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController1 = navHostFragment.navController

        navController1.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentHome -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentKeranjang -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentProfile2 -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }
    }
}