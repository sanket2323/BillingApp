package com.example.billingapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billingapp.R

@Composable
fun HomePage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(Modifier.height(80.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                painter = painterResource(R.drawable.onion_png_38743),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            Spacer(Modifier.height(80.dp))
            Button(
                onClick = {
                    navController.navigate("makeBill")
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Make New Bill",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {

                    navController.navigate("adminLogin")
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Admin Login",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


