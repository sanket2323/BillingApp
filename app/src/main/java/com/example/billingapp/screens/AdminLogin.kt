package com.example.billingapp.screens

import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billingapp.Global
import com.example.billingapp.R

@Composable
fun AdminLogin(navController: NavController) {

    var adminId by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var adminPass by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility) painterResource(id = R.drawable.baseline_visibility_24)
    else painterResource(id = R.drawable.baseline_visibility_off_24)

    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(value = adminId, onValueChange = {
                adminId = it
            },
                shape = RoundedCornerShape(10.dp),
                enabled = true, placeholder = {
                Text(text = "Enter Id")
            }, label = {
                Text(text = "Enter Id")
            }, trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = "null",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                )
            }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = adminPass,
                shape = RoundedCornerShape(10.dp),
                onValueChange = {
                    adminPass = it
                },
                enabled = true,
                placeholder = { Text(text = "Enter Password") },
                label = { Text(text = "Enter Password") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }, modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility Icon",
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation()

            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {

                if (!TextUtils.isEmpty(adminId.text) &&
                    !TextUtils.isEmpty(adminPass.text)
                ) {
                    if (Global.listOfAdminIdAndPassword[adminId.text] == adminPass.text) {
                        Global.adminId = adminId.text
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        navController.navigate("displayList") {
                            popUpTo("adminLogin") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "InCorrect Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Enter All Fields", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(
                    text = "Log In", fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


