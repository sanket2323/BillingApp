package com.example.billingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.billingapp.screens.AdminLogin
import com.example.billingapp.screens.DisplayList

import com.example.billingapp.ui.theme.BillingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BillingAppTheme {
                Global.apply {
                    listOfAdminIdAndPassword["sanket"] = "admin@123"
                }
              AppNavigation()
            }
        }
    }
}

