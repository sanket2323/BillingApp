package com.example.billingapp.screens

import android.util.Log
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
    val cardDataList = ArrayList<ArrayList<ArrayList<CardData>>>()



    LaunchedEffect(Unit) {
        try {
            val db = FirebaseFirestore.getInstance()
            val bills = mutableListOf<BillingModelClass>()

            val expenseDetails = ArrayList<ExpenseModelClass>()
            db.collection("users").get().addOnSuccessListener { users ->
                for (user in users) {
                    val docId = db.collection("users").document(user.id).id
                    db.collection("users")
                        .document(user.id)
                        .collection("billingDetails")
                        .get()
                        .addOnSuccessListener {
                            for (doc in it) {
                                billDetail.add(
                                    BillingDetails(
                                        nameOfFarmer = user.data["nameOfFramer"].toString(),
                                        cityName = doc["cityName"].toString(),
                                        date = doc["date"].toString(),
                                        billNo = doc["billNo"].toString(),
                                        totalPayAbleAmount = doc["totalPayableAmount"].toString(),
                                        id = docId,
                                    )
                                )
                            }
                            println("GGWP:" + billDetail)
                        }.addOnFailureListener {
                            println("Error in billDetails Loading $it")
                        }
//

                    val expenseRef = user.reference.collection("expense")
                    expenseRef.get().addOnSuccessListener { docs ->
                        for (doc in docs) {
                            expenseDetail.add(
                                ExpenseModelClass(
                                    aadat = doc["aadat"].toString(),
                                    hamali = doc["hamali"].toString(),
                                    tolai = doc["tolai"].toString(),
                                    varai = doc["varai"].toString(),
                                    bharai = doc["bharai"].toString(),
                                    travelingExpense = doc["travelingExpense"].toString(),
                                    uchal = doc["uchal"].toString(),
                                    bardhana = doc["bardhana"].toString(),
                                    otherExpense = doc["otherExpense"].toString(),
                                    id = docId
                                )
                            )
                            println("GGWP:" + expenseDetail)
                        }
                    }.addOnFailureListener {
                        println("Error in expense$it")
                    }

                    bills.add(
                        BillingModelClass(
                            billingDetails = billDetail,
                            cardData =(cardDataList) ,
                            expenseModelClass = expenseDetail,
                        )
                    )
                }

                billingList = bills
                isLoading = false
                println(billingList)
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
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column {
                    Text(
                        text = "Billing Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        println(billingList)
                        items(billingList) { bill ->
                            BillingCard(
                                bill = bill,
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BillingCard(bill: BillingModelClass, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick },

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            bill.billingDetails.forEach { billdetail ->
                Text(text = "Name of Farmer: ${billdetail.nameOfFarmer}")
                Text(text = "Date: ${billdetail.date}")
                Text(text ="Total payable :${billdetail.totalPayAbleAmount}")
            }
        }
    }
}

