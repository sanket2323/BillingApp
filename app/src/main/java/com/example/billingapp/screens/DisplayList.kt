package com.example.billingapp.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billingapp.Global
import com.example.billingapp.model.BillingDetails
import com.example.billingapp.model.BillingModelClass
import com.example.billingapp.model.CardData
import com.example.billingapp.model.ExpenseModelClass
import com.google.firebase.firestore.FirebaseFirestore

//@Composable
//fun DisplayList(navController: NavController) {
//
//    var billingList by remember { mutableStateOf<List<BillingModelClass>>(ArrayList()) }
//    var billsMain = mutableListOf<BillingModelClass>()
//    var isLoading by remember { mutableStateOf(true) }
//    var selectedBill by remember { mutableStateOf<BillingModelClass?>(null) }
//    val billDetail = ArrayList<BillingDetails>()
//    val expenseDetail = ArrayList<ExpenseModelClass>()
//    val cardList = ArrayList<CardData>()
//    var ListofBillDetails by remember { mutableStateOf(ArrayList<BillingDetails>()) }
//    var searchName by remember { mutableStateOf("") }
//
//
//
//    LaunchedEffect(Unit) {
//        try {
//            val db = FirebaseFirestore.getInstance()
//            val bills = ArrayList<BillingModelClass>()
//
//            val expenseDetails = ArrayList<ExpenseModelClass>()
//            db.collection("users").get().addOnSuccessListener { users ->
//                for (user in users) {
//                    val docId = db.collection("users").document(user.id).id
//                    db.collection("users").document(user.id).collection("billingDetails").get()
//                        .addOnSuccessListener {
//                            for (doc in it) {
//                                billDetail.add(
//                                    BillingDetails(
//                                        nameOfFarmer = doc["nameOfFramer"].toString(),
//                                        cityName = doc["cityName"].toString(),
//                                        date = doc["date"].toString(),
//                                        billNo = doc["billNo"].toString(),
//                                        totalPayAbleAmount = doc["totalPayableAmount"].toString(),
//                                        id = docId,
//                                    )
//                                )
//                            }
//                            ListofBillDetails = billDetail
//                            bills.add(
//                                BillingModelClass(
//                                    billingDetails = billDetail,
//                                    cardData = cardList,
//                                    expenseModelClass = expenseDetail,
//                                )
//                            )
//                            billsMain.add(
//                                BillingModelClass(
//                                    billingDetails = billDetail,
//                                    cardData = cardList,
//                                    expenseModelClass = expenseDetail,
//                                )
//                            )
//                            println("Chut"+bills)
//                            println("Bill Details:" + ListofBillDetails)
//                            println("Bill Data:" + billDetail)
//                        }.addOnFailureListener {
//                            println("Error in billDetails Loading $it")
//                        }
//                }
//                billingList = billsMain
//                isLoading = false
//            }
//
//        } catch (e: Exception) {
//            println("Error fetching data: $e")
//        }
//    }
//
//    Scaffold { paddingValues ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//
//            AnimatedVisibility(visible = ListofBillDetails.isEmpty()) {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Bottom
//                ) {
//                    Text(
//                        text = "No Registered Bills Available",
//                        fontSize = 24.sp,
//                        textAlign = TextAlign.Center
//                    )
//                }
//
//            }
//            AnimatedVisibility(visible = ListofBillDetails.isNotEmpty() && !isLoading) {
//                Column {
//                    OutlinedTextField(
//                        value = searchName,
//                        onValueChange = {
//                            searchName = it
//                        },
//                        label = { Text("Search") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        shape = RoundedCornerShape(10.dp),
//
//                        )
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(start = 8.dp, end = 8.dp),
//                        verticalArrangement = Arrangement.spacedBy(5.dp)
//                    ) {
//                        for(bill in billingList) {
//                            item {
//                                CardView(bill)
//                            }
//                        }
//                    }
//                }
//
//            }
//            AnimatedVisibility(visible = isLoading || ListofBillDetails.isEmpty()) {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//
//            }
//        }
//    }
//}
//
//@Composable
//fun CardView(wholeBill: BillingModelClass) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable {
//                Global.currentModel = wholeBill
//                Log.d("TAG", "CardView: ${Global.currentModel}")
////                println("All Info${wholeBill.cardData}\n Expenses Card: ${wholeBill
////                .expenseModelClass}")
//            },
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
//    ) {
//        Text(
//            wholeBill.billingDetails[0].nameOfFarmer,
//            modifier = Modifier.padding(6.dp),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.SemiBold
//        )
//        Text(
//            "Total Amount Payable:" + wholeBill.billingDetails[0].totalPayAbleAmount,
//            modifier = Modifier.padding(6.dp),
//            fontSize = 18.sp,
//            fontWeight = FontWeight.W400
//        )
//    }
//
//
//}

@Composable
fun DisplayList(navController: NavController) {

    var billingList by remember { mutableStateOf<List<BillingModelClass>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedBill by remember { mutableStateOf<BillingModelClass?>(null) }
    val expenseDetail = ArrayList<ExpenseModelClass>()
    val cardList = ArrayList<CardData>()

    LaunchedEffect(Unit) {
        try {
            val db = FirebaseFirestore.getInstance()
            val bills = mutableListOf<BillingModelClass>()
            val billDetail = ArrayList<BillingDetails>()

            db.collection("users").get().addOnSuccessListener { users ->
                for (user in users) {
                    // Create a new list for each user's billing details

                    val docId = db.collection("users").document(user.id).id
                    db.collection("users").document(user.id).collection("billingDetails").get()
                        .addOnSuccessListener { documents ->
                            for (doc in documents) {
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
                            // Add a new BillingModelClass with a unique billDetail list

                        }.addOnFailureListener {
                            println("Error in billDetails Loading $it")
                            isLoading = false
                        }

                    db.collection("users").document(user.id).collection("expense").get()
                        .addOnSuccessListener {
                            for (doc in it){
                                expenseDetail.add(
                                    ExpenseModelClass(
                                        aadat = doc["aadat"].toString(),
                                        hamali =doc["hamali"].toString(),
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
                            }
                            println("Expense Data: $expenseDetail")
                        }.addOnFailureListener {
                            println("Error in billDetails Loading $it")
                            isLoading = false
                        }
                }
            }
            bills.add(
                BillingModelClass(
                    billingDetails = billDetail,
                    cardData = cardList,
                    expenseModelClass = expenseDetail,
                )
            )
            // Update billingList with a new list to trigger recomposition
            billingList = bills.toList()
            isLoading = false
        } catch (e: Exception) {
            println("Error fetching data: $e")
            isLoading = false
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (billingList.isEmpty()) {
                Text(
                    text = "No Registered Bills Available",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(billingList) { bill ->
                        CardView(billdetail = bill) {
                            selectedBill = bill
                        }
                    }
                }
            }

            selectedBill?.let { bill ->
                AlertDialog(
                    onDismissRequest = { selectedBill = null },
                    confirmButton = {
                        Button(onClick = { selectedBill = null }) {
                            Text("Close")
                        }
                    },
                    title = { Text("Billing Details") },
                    text = {
                        Column {
                            Text("Farmer: ${bill.billingDetails.firstOrNull()?.nameOfFarmer}")
                            Text("City: ${bill.billingDetails.firstOrNull()?.cityName}")
                            Text("Date: ${bill.billingDetails.firstOrNull()?.date}")
                            Text("Bill No: ${bill.billingDetails.firstOrNull()?.billNo}")
                            Text("Total Payable: ${bill.billingDetails.firstOrNull()?.totalPayAbleAmount}")
                            Text("Card Data: ${bill.cardData}")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CardView(billdetail: BillingModelClass, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Farmer: ${billdetail.billingDetails.firstOrNull()?.nameOfFarmer ?: "N/A"}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Total Payable: ${billdetail.billingDetails.firstOrNull()?.totalPayAbleAmount ?: "N/A"}",
                fontSize = 16.sp
            )
        }
    }
}



