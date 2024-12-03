package com.example.billingapp.screens

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
    var isSearching by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        delay(2000)
        val db = FirebaseFirestore.getInstance()

        try {
            db.collection("users").get().addOnSuccessListener { users ->


                for (user in users) {
                    db.collection("users").document(user.id).collection("cardDetails")
                        .whereEqualTo("id", user["id"].toString()).get()
                        .addOnSuccessListener { cards ->

                            for (card in cards) {
                                listOfCard.add(
                                    CardData(
                                        quantity = card["quantity"].toString(),
                                        weight = card["weight"].toString(),
                                        price = card["price"].toString(),
                                        totalPrice = card["totalPrice"].toString(),
                                        typeOfProduct = card["typeOfProduct"].toString(),
                                        id = card["id"].toString(),
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
                                    phoneNumber = user["phoneNumber"].toString(),
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

    val billsList = searchName

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
                            listOfBills = if (!searchName.isEmpty()) {
                                val list = search(it, listOfBills)
                                list
                            } else {
                                listOfBills
                            }
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
            .background(color = Color.Transparent)
            .padding(top = 4.dp),
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
            Row{
                Icon(Icons.Default.Info, "Info Button", modifier = Modifier
                    .size(40.dp)
                    .padding(
                        end = 10.dp
                    )
                    .clickable {
                        Global.selectedMandal = wholeBill
                        val intent = Intent(context, ViewBillDetails::class.java)
                        context.startActivity(intent)
                    })

                Icon(
                    Icons.Default.Call, "PhoneCall", modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 10.dp
                        )
                        .clickable {
                            try {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel: ${wholeBill.phoneNumber}")
                                }
                                context.startActivity(intent)
                            }
                            catch(e:Exception){
                                Log.d("TAG", "CardView: ${e.message}")
                            }

                        }

                )
            }


        }


    }


}


//fun search(textFieldValue: TextFieldValue, listOfMandals: ArrayList<Mandal>): ArrayList<Mandal> {
//    var list = ArrayList<Mandal>()
//    if (!TextUtils.isEmpty(textFieldValue.text)) {
//        for (mandal in listOfMandals) {
//            if (mandal.nameOfMandal.toString().contains(textFieldValue.text, true)) {
//                list.add(mandal)
//            }
//        }
//    } else {
//        list = listOfMandals
//    }
//    return list
//}

fun search(
    searchName: String, listOfBills: ArrayList<BillingModelClass>
): ArrayList<BillingModelClass> {
    var list = ArrayList<BillingModelClass>()
    if (!TextUtils.isEmpty(searchName)) {
        for (bill in listOfBills) {
            if (bill.nameOfFarmer.contains(searchName, true)) {
                list.add(bill)
            }
        }
    } else {
        list = listOfBills
    }
    return list
}

fun doesMatchSearchQuery(query: String, billingModelClass: BillingModelClass): Boolean {
    val matchingCombination = listOf(
        "${billingModelClass.nameOfFarmer}",
        "${billingModelClass.cityName}",
        "${billingModelClass.date}",
        "${billingModelClass.billNo}",
        "${billingModelClass.nameOfFarmer.first()}"
    )

    return matchingCombination.any {
        it.contains(query, ignoreCase = true)
    }
}