package com.example.scavhunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.scavhunt.fragment.create.CreateHuntViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val createHuntViewModel: CreateHuntViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up navigation
        val navView : BottomNavigationView = findViewById(R.id.main_nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
            as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

    }
}