package com.example.restart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.restart.R

import kotlinx.android.synthetic.main.activity_weather.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class WeatherActivity : AppCompatActivity(), KodeinAware{
    private val _parentKodein by kodein()
    override val kodein : Kodein  by Kodein.lazy {
        extend(_parentKodein)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = null
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
