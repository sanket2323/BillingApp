package com.example.billingapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.billingapp.screens.AdminLogin
import com.example.billingapp.screens.DisplayList
import com.example.billingapp.screens.HomePage
import com.example.billingapp.screens.MakeBillScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(navController)
        }
        composable("makeBill") {
            MakeBillScreen(navController)
        }
        composable("adminLogin") {
            AdminLogin(navController)
        }
        composable("displayList") {
            DisplayList(navController)
        }

    }
}