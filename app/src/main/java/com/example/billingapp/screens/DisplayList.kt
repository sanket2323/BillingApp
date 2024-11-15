package com.example.billingapp.screens

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billingapp.Global
import com.example.billingapp.ViewBillDetails
import com.example.billingapp.model.BillingModelClass
import com.example.billingapp.model.CardData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun DisplayList(navController: NavController) {

    var searchName by remember { mutableStateOf("") }
    var listOfBill = ArrayList<BillingModelClass>()
    var listOfCard = ArrayList<CardData>()
    var isLoading by remember { mutableStateOf(true) }
    var listOfBills by remember { mutableStateOf(ArrayList<BillingModelClass>()) }


    LaunchedEffect(Unit) {
        delay(2000)
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection("users").get().addOnSuccessListener { users ->

                for (user in users) {
                    db.collection("users").document(user.id).collection("cardDetails").get()
                        .addOnSuccessListener { cards ->
                            for (card in cards) {
                                listOfCard.add(
                                    CardData(
                                        quantity = card["quantity"].toString(),
                                        weight = card["weight"].toString(),
                                        price = card["price"].toString(),
                                        totalPrice = card["totalPrice"].toString(),
                                        typeOfProduct = card["typeOfProduct"].toString(),
                                        id = card.id,
                                    )
                                )
                            }
                            listOfBill.add(
                                BillingModelClass(
                                    aadat = user["aadat"].toString(),
                                    hamali = user["hamali"].toString(),
                                    tolai = user["tolai"].toString(),
                                    varai = user["varai"].toString(),
                                    bharai = user["bharai"].toString(),
                                    travelingExpense = user["travelingExpense"].toString(),
                                    uchal = user["uchal"].toString(),
                                    bardhana = user["bardhana"].toString(),
                                    otherExpense = user["otherExpense"].toString(),
                                    totalExpense = user["totalExpense"].toString(),
                                    id = user["id"].toString(),
                                    nameOfFarmer = user["nameOfFramer"].toString(),
                                    cityName = user["cityName"].toString(),
                                    date = user["date"].toString(),
                                    billNo = user["billNo"].toString(),
                                    totalPayAbleAmount = user["totalPayableAmount"].toString(),
                                    cardData = listOfCard
                                )
                            )
                            listOfBills = listOfBill
                            println("List $listOfBill")
                        }
                }
            }.addOnFailureListener {
                Log.d("TAG", "DisplayList: ${it.message}")
            }
            isLoading = false
        } catch (e: Exception) {
            println(e.message)
        }

    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            AnimatedVisibility(visible = listOfBills.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchName,
                        onValueChange = {
                            searchName = it
                        },
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(10.dp),
                    )

                    LazyColumn {
                        items(listOfBills) { bill ->
                            CardView(
                                wholeBill = bill,
                            )
                        }
                    }
                }

            }

            AnimatedVisibility(visible = listOfBills.isEmpty()) {
                OutlinedTextField(
                    value = searchName,
                    onValueChange = {
                        searchName = it
                    },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "No Data Found",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            AnimatedVisibility(visible = isLoading) {
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
fun CardView(wholeBill: BillingModelClass) {
    var context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Global.selectedMandal = wholeBill
                val intent = Intent(context, ViewBillDetails::class.java)
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp),
//

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    wholeBill.nameOfFarmer,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Date:" + wholeBill.date,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                )
                Text(
                    "Total Amount Payable:" + wholeBill.totalPayAbleAmount + " ₹",
                    modifier = Modifier.padding(4.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Icon(
                Icons.Default.Info, "Info Button", modifier = Modifier.size(30.dp)
            )

        }


    }


}
