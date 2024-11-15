package com.example.billingapp.screens

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billingapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MakeBillScreen(navController: NavController) {

    var nameOfFramer by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var cityName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var date by remember {
        mutableStateOf("")
    }
    var billNo by remember {
        mutableStateOf(TextFieldValue(""))
    }


    data class CardData(
        var quantity: String = "",
        var weight: String = "",
        var price: String = "",
        var totalPrice: String = "",
        var typeOfProduct: String = "कांदा",
        var id:String =""
    )

    val cardDataStates = remember { mutableStateListOf<CardData>() }
    var totalofAllCards by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        cardDataStates.add(CardData())
    }

    val db = FirebaseFirestore.getInstance()
    var isLoading by remember { mutableStateOf(false) }


    //expense wala part
    var aadat by remember { mutableStateOf("") }
    var hamali by remember { mutableStateOf("") }
    var tolai by remember { mutableStateOf("") }
    var varai by remember { mutableStateOf("") }
    var bharai by remember { mutableStateOf("") }
    var travelingExpense by remember { mutableStateOf("") }
    var uchal by remember { mutableStateOf("") }
    var bardhana by remember { mutableStateOf("") }
    var otherExpense by remember { mutableStateOf("") }
    var totalPayAbleAmount by remember { mutableStateOf("") }

    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                AnimatedVisibility(visible = isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = it.calculateTopPadding(),
                            end = 10.dp,
                            start = 10.dp,
                            bottom = it.calculateBottomPadding()
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    UpperSection()
                    HorizontalDivider(
                        thickness = 2.dp, color = Color.Gray
                    )

                    Spacer(Modifier.height(10.dp))
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        OutlinedTextField(value = nameOfFramer,
                            onValueChange = { nameOfFramer = it },
                            placeholder = { Text("शेतकऱ्याचे नाव") },
                            label = { Text("शेतकऱ्याचे नाव") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(value = cityName,
                                onValueChange = { cityName = it },
                                placeholder = { Text("शहर") },
                                label = { Text("शहर") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(0.75f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            OutlinedTextField(value = billNo,
                                onValueChange = { billNo = it },
                                placeholder = { Text("बिल क्रमांक") },
                                label = { Text("बिल क्रमांक") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(0.25f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )


                        }


                        //Date Picker
                        val context = LocalContext.current
                        val calendar = Calendar.getInstance()
                        val year = calendar[Calendar.YEAR]
                        val month = calendar[Calendar.MONTH]
                        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
                        val datePicker = DatePickerDialog(
                            context,
                            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                                date = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                            },
                            year,
                            month,
                            dayOfMonth
                        )
                        datePicker.datePicker.minDate = calendar.timeInMillis


                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(text = "तारीख")
                                Text(text = date)
                            }
                            Button(onClick = {
                                datePicker.show()
                            }) {
                                Text(text = "Select date", fontSize = 12.sp)
                            }
                        }

                        //Card for adding product


                        //this is for cards used for adding items
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(310.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyColumn {
                                items(cardDataStates.size) { index -> // Use items() with cardCount
                                    val cardData = cardDataStates[index]
                                    var totalprice by remember { mutableStateOf(cardData.totalPrice) }
                                    var weightOfProduct by remember { mutableStateOf(cardData.weight) }
                                    var priceOfProduct by remember {
                                        mutableStateOf(
                                            cardData.price ?: "0"
                                        )
                                    }
                                    var quantityOfProduct by remember { mutableStateOf(cardData.quantity) }
                                    var typeOfProduct by remember { mutableStateOf(cardData.typeOfProduct) }

                                    Card(
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            OutlinedTextField(value = typeOfProduct,
                                                onValueChange = { newvalue ->
                                                    typeOfProduct = newvalue
                                                    cardDataStates[index] =
                                                        cardDataStates[index].copy(typeOfProduct = newvalue)
                                                },
                                                placeholder = { Text("मालाचा प्रकार") },
                                                label = { Text("मालाचा प्रकार") },
                                                shape = RoundedCornerShape(10.dp),
                                                modifier = Modifier.weight(0.75f)
                                            )

                                            OutlinedTextField(
                                                value = quantityOfProduct,
                                                onValueChange = { newValueQuantity ->
                                                    quantityOfProduct = newValueQuantity
                                                    cardDataStates[index] =
                                                        cardDataStates[index].copy(quantity = newValueQuantity)
                                                },
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
                                                value = weightOfProduct,
                                                onValueChange = { newValueWeight ->
                                                    weightOfProduct = newValueWeight
                                                    cardDataStates[index] =
                                                        cardDataStates[index].copy(weight = newValueWeight)
                                                },
                                                placeholder = { Text("वजन (kg)") },
                                                label = { Text("वजन (kg)") },
                                                shape = RoundedCornerShape(10.dp),
                                                modifier = Modifier.weight(1f),
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                            )
                                            OutlinedTextField(
                                                value = priceOfProduct,
                                                onValueChange = { newValuePrice ->
                                                    priceOfProduct = newValuePrice
                                                    cardDataStates[index] =
                                                        cardDataStates[index].copy(price = newValuePrice)
                                                },
                                                placeholder = { Text("भाव (₹ per kg)") },
                                                label = { Text("भाव (₹ per kg)") },
                                                shape = RoundedCornerShape(10.dp),
                                                modifier = Modifier.weight(1f),
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                            )

                                            val weightOfProductin =
                                                weightOfProduct.toIntOrNull() ?: 0
                                            val priceOfSingleProduct =
                                                priceOfProduct.toIntOrNull() ?: 0

//                                    var exp1 = aadat.toIntOrNull() ?: 0

                                            totalprice =
                                                (weightOfProductin * priceOfSingleProduct).toString()
                                            cardDataStates[index] = cardDataStates[index].copy(
                                                totalPrice = totalprice
                                            )



                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(text = "एकूण")
                                                Text(
                                                    text = "${totalprice}₹",
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 20.sp
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp)) // Add spacing between cards
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))


                        }
                        totalofAllCards = cardDataStates.sumOf { data ->
                            data.totalPrice.toIntOrNull() ?: 0
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "एकूण: ${totalofAllCards}₹",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                cardDataStates.add(CardData())
                            }) {
                                Text("Add Another Card")
                            }

                            Button(onClick = {
                                if (cardDataStates.size > 1) {
                                    cardDataStates.removeLast()
                                }

                            }) {
                                Text("Delete one card")
                            }
                        }
                        HorizontalDivider(
                            thickness = 0.75.dp, color = Color.Gray
                        )


                        //this is expense

                        var exp1 = aadat.toIntOrNull() ?: 0
                        var exp2 = hamali.toIntOrNull() ?: 0
                        var exp3 = tolai.toIntOrNull() ?: 0
                        var exp4 = varai.toIntOrNull() ?: 0
                        var exp5 = bharai.toIntOrNull() ?: 0
                        var exp6 = travelingExpense.toIntOrNull() ?: 0
                        var exp7 = uchal.toIntOrNull() ?: 0
                        var exp8 = bardhana.toIntOrNull() ?: 0
                        var exp9 = otherExpense.toIntOrNull() ?: 0

                        var totalExpense by remember {
                            mutableStateOf("")
                        }


                        Text(
                            text = "खर्चाचा तपशील",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )



                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            OutlinedTextField(value = aadat,
                                onValueChange = { aadat = it },
                                placeholder = { Text("आडत") },
                                label = { Text("आडत") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                            )
                            OutlinedTextField(value = hamali,
                                onValueChange = { hamali = it },
                                placeholder = { Text("हमाली") },
                                label = { Text("हमाली") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(value = tolai,
                                onValueChange = { tolai = it },
                                placeholder = { Text("तोलाई") },
                                label = { Text("तोलाई") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            OutlinedTextField(value = varai,
                                onValueChange = { varai = it },
                                placeholder = { Text("वाराई") },
                                label = { Text("वाराई") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                            )
                            OutlinedTextField(value = bharai,
                                onValueChange = { bharai = it },
                                placeholder = { Text("भराई") },
                                label = { Text("भराई") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(value = travelingExpense,
                                onValueChange = { travelingExpense = it },
                                placeholder = { Text("मोटर भाडे") },
                                label = { Text("मोटर भाडे") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            OutlinedTextField(value = uchal,
                                onValueChange = { uchal = it },
                                placeholder = { Text("उचल") },
                                label = { Text("उचल") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                            )
                            OutlinedTextField(value = bardhana,
                                onValueChange = { bardhana = it },
                                placeholder = { Text("बारदाणा") },
                                label = { Text("बारदाणा") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(value = otherExpense,
                                onValueChange = { otherExpense = it },
                                placeholder = { Text("ईतर") },
                                label = { Text("ईतर") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                        }

                        totalExpense =
                            (exp1 + exp2 + exp3 + exp4 + exp5 + exp6 + exp7 + exp8 + exp9).toString()
                        Text(
                            text = "Total Expense: ${totalExpense}₹",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        HorizontalDivider(
                            thickness = 0.75.dp, color = Color.Gray
                        )
                        totalPayAbleAmount = (totalofAllCards - totalExpense.toInt()).toString()
                        Text(
                            text = "Total Payable Amount: ${totalPayAbleAmount}₹",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                            isLoading = true
                        }) {
                            Text("Submit")
                        }

                        if (isLoading) {
                            LaunchedEffect(Unit) {
                                delay(2000)

                                val hashMapItems = HashMap<String, String>()
                                val hashMapCardItems = HashMap<String, String>()
                                val newDocumentRef = db.collection("users").document().id
                                var framerDocId = "${nameOfFramer.text}_${newDocumentRef}"


                                hashMapItems["nameOfFramer"] = nameOfFramer.text
                                hashMapItems["cityName"] = cityName.text
                                hashMapItems["date"] = date
                                hashMapItems["billNo"] = billNo.text
                                hashMapItems["totalPayableAmount"] = totalPayAbleAmount
                                hashMapItems["id"] = newDocumentRef
                                hashMapItems["aadat"] = aadat
                                hashMapItems["hamali"] = hamali
                                hashMapItems["tolai"] = tolai
                                hashMapItems["varai"] = varai
                                hashMapItems["bharai"] = bharai
                                hashMapItems["travelingExpense"] = travelingExpense
                                hashMapItems["uchal"] = uchal
                                hashMapItems["bardhana"] = bardhana
                                hashMapItems["otherExpense"] = otherExpense
                                hashMapItems["totalExpense"] = totalExpense

                                db.collection("users").document(framerDocId)
                                    .set(hashMapItems)
                                    .addOnSuccessListener {
                                        isLoading = false
                                        for (cardData in cardDataStates) {
                                            hashMapCardItems["quantity"] = cardData.quantity
                                            hashMapCardItems["weight"] = cardData.weight
                                            hashMapCardItems["price"] = cardData.price
                                            hashMapCardItems["totalPrice"] = cardData.totalPrice
                                            hashMapCardItems["typeOfProduct"] = cardData.typeOfProduct
                                            hashMapCardItems["id"] = newDocumentRef
                                            db.collection("users").document(framerDocId)
                                                .collection("cardDetails")
                                                .add(hashMapCardItems)
                                                .addOnFailureListener { exception ->
                                                    println(exception.message)
                                                    Toast.makeText(
                                                        context,
                                                        "Failed to add card details: ${exception.message}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                        }

                                        Toast.makeText(
                                            context,
                                            "Data Added to Firebase",
                                            Toast.LENGTH_SHORT).show()
                                        navController.navigate("home") {
                                            popUpTo("makeBill") { inclusive = true }
                                        }

                                    }
                                    .addOnFailureListener{
                                        println(it.message)
                                        Toast.makeText(
                                            context,
                                            "Data Not Added to Firebase",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        isLoading = false
                                    }
                            }
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun UpperSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.onion_png_38743),
            null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(85.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "विजयकुमार भिमराव जगताप",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "गाळा नं. १४४ बार्शी. जि.सोलापूर",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = "9423336041, 9823222323",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
            )
        }

    }
}


//Date Picker

//onClick = {
//
//
//
//}
