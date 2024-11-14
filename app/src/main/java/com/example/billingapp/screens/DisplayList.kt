package com.example.billingapp.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billingapp.model.BillingDetails
import com.example.billingapp.model.BillingModelClass
import com.example.billingapp.model.CardData
import com.example.billingapp.model.ExpenseModelClass
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DisplayList(navController: NavController) {

    var billingList by remember { mutableStateOf<List<BillingModelClass>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedBill by remember { mutableStateOf<BillingModelClass?>(null) }
    val billDetail = ArrayList<BillingDetails>()
    val expenseDetail = ArrayList<ExpenseModelClass>()
    val cardList = ArrayList<CardData>()
    var ListofBillDetails by remember { mutableStateOf(ArrayList<BillingDetails>()) }
    var searchName by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        try {
            val db = FirebaseFirestore.getInstance()
            val bills = mutableListOf<BillingModelClass>()

            val expenseDetails = ArrayList<ExpenseModelClass>()
            db.collection("users").get().addOnSuccessListener { users ->
                for (user in users) {
                    val docId = db.collection("users").document(user.id).id
                    db.collection("users").document(user.id).collection("billingDetails").get()
                        .addOnSuccessListener {
                            for (doc in it) {
                                billDetail.add(
                                    BillingDetails(
                                        nameOfFarmer = doc["nameOfFramer"].toString(),
                                        cityName = doc["cityName"].toString(),
                                        date = doc["date"].toString(),
                                        billNo = doc["billNo"].toString(),
                                        totalPayAbleAmount = doc["totalPayableAmount"].toString(),
                                        id = docId,
                                    )
                                )
                            }
                            ListofBillDetails = billDetail
                            println("Bill Details:" + ListofBillDetails)
                            println("Bill Data:" + billDetail)
                        }.addOnFailureListener {
                            println("Error in billDetails Loading $it")
                        }




                    bills.add(
                        BillingModelClass(
                            billingDetails = billDetail,
                            cardData = cardList,
                            expenseModelClass = expenseDetail,
                        )
                    )
                }
                billingList = bills
                isLoading = false
            }

        } catch (e: Exception) {
            println("Error fetching data: $e")
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            AnimatedVisibility(visible = ListofBillDetails.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "No Registered Bills Available",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }

            }
            AnimatedVisibility(visible = ListofBillDetails.isNotEmpty() && !isLoading) {
                Column {
                    OutlinedTextField(
                        value = searchName,
                        onValueChange = {
                            searchName = it
                        },
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth().padding(16.dp),
                        shape = RoundedCornerShape(10.dp),

                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(start = 8.dp, end = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(billDetail) {
                            CardView(billdetail = it)
                        }
                    }
                }

            }
            AnimatedVisibility(visible = isLoading || ListofBillDetails.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }

            }
        }
    }
}

@Composable
fun CardView(billdetail: BillingDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {

        Text(
            billdetail.nameOfFarmer,
            modifier = Modifier.padding(6.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            "Total Amount Payable:" + billdetail.totalPayAbleAmount,
            modifier = Modifier.padding(6.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.W400
        )
    }
}


