package com.example.scavhunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
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