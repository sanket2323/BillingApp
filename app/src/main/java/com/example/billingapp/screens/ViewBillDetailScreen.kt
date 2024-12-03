package com.example.billingapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billingapp.model.BillingModelClass
import com.example.billingapp.model.CardData
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ViewBillDetailScreen(bill: BillingModelClass) {
    var db = FirebaseFirestore.getInstance()

    Scaffold {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                        start = 14.dp,
                        end = 14.dp
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement
                    .spacedBy(10.dp)
            ) {
                Text(
                    text = "Bill Detail Screen",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = bill.nameOfFarmer,
                    onValueChange = {},
                    placeholder = { Text("शेतकऱ्याचे नाव") },
                    label = { Text(text = "शेतकऱ्याचे नाव") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = bill.cityName,
                        onValueChange = { },
                        placeholder = { Text("शहर") },
                        label = { Text("शहर") },
                        shape = RoundedCornerShape(10.dp),
                        enabled = false,
                        modifier = Modifier.weight(0.25f),
                    )

                    OutlinedTextField(
                        value = bill.billNo,
                        onValueChange = { },
                        placeholder = { Text("बिल क्रमांक") },
                        label = { Text("बिल क्रमांक") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(0.25f),
                        enabled = false,
                    )

                    println(bill.phoneNumber)
                    OutlinedTextField(
                        value = bill.date,
                        onValueChange = { },
                        placeholder = { Text("तारीख") },
                        label = { Text("तारीख") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(0.25f),
                        enabled = false,
                    )
                }

                HorizontalDivider(
                    thickness = 0.75.dp, color = Color.Gray
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val filteredList = bill.cardData.filter {
                        it.id == bill.id

                    }
                    println("FilterList " + filteredList)

                    if (filteredList.isEmpty()) {
                        filteredList.forEach { card ->
                            Text("No cards")
                        }
                    } else {
                        filteredList.forEach {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    OutlinedTextField(
                                        value = it.typeOfProduct,
                                        onValueChange = {},
                                        enabled = false,
                                        placeholder = { Text("मालाचा प्रकार") },
                                        label = { Text("मालाचा प्रकार") },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(0.75f)
                                    )

                                    OutlinedTextField(
                                        value = it.quantity,
                                        onValueChange = { },
                                        enabled = false,
                                        placeholder = { Text("नग") },
                                        label = { Text("नग") },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(0.25f),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )


                                }


                                Spacer(Modifier.height(6.dp))

                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {

                                    OutlinedTextField(
                                        value = it.weight,
                                        onValueChange = {},
                                        enabled = false,
                                        placeholder = { Text("वजन (kg)") },
                                        label = { Text("वजन (kg)") },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                    OutlinedTextField(
                                        value = it.price,
                                        onValueChange = {},
                                        enabled = false,
                                        placeholder = { Text("भाव (₹ per kg)") },
                                        label = { Text("भाव (₹ per kg)") },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = "एकूण")
                                        Text(
                                            text = "${it.totalPrice}₹",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                }
                HorizontalDivider(
                    thickness = 0.75.dp, color = Color.Gray
                )



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        value = bill.aadat,
                        onValueChange = { },
                        placeholder = { Text("आडत") },
                        label = { Text("आडत") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        enabled = false

                    )
                    OutlinedTextField(
                        value = bill.hamali,
                        onValueChange = { },
                        placeholder = { Text("हमाली") },
                        label = { Text("हमाली") },
                        enabled = false,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = bill.tolai,
                        onValueChange = { },
                        placeholder = { Text("तोलाई") },
                        label = { Text("तोलाई") },
                        enabled = false,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        value = bill.varai,
                        onValueChange = { },
                        enabled = false,
                        placeholder = { Text("वाराई") },
                        label = { Text("वाराई") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )
                    OutlinedTextField(
                        value = bill.bharai,
                        onValueChange = { },
                        placeholder = { Text("भराई") },
                        label = { Text("भराई") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        enabled = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = bill.travelingExpense,
                        onValueChange = { },
                        placeholder = { Text("मोटर भाडे") },
                        label = { Text("मोटर भाडे") },
                        shape = RoundedCornerShape(10.dp),
                        enabled = false,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        value = bill.uchal,
                        onValueChange = { },
                        placeholder = { Text("उचल") },
                        label = { Text("उचल") },
                        enabled = false,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )
                    OutlinedTextField(
                        value = bill.bardhana,
                        onValueChange = { },
                        placeholder = { Text("बारदाणा") },
                        label = { Text("बारदाणा") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        enabled = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = bill.otherExpense,
                        onValueChange = { },
                        enabled = false,
                        placeholder = { Text("ईतर") },
                        label = { Text("ईतर") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                }

                Text(
                    "Total Expense:" + bill.totalExpense +"₹",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    thickness = 0.75.dp, color = Color.Gray
                )



                Text(
                    "कुल:" + bill.totalPayAbleAmount + "₹",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )


            }
        }
    }
}




