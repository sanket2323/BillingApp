package com.example.billingapp.model

data class BillingModelClass(
    var aadat: String,
    var hamali: String,
    var tolai: String,
    var varai: String,
    var bharai: String,
    var travelingExpense: String,
    var uchal: String,
    var bardhana: String,
    var otherExpense: String,
    var totalExpense: String,
    var id: String,
    val nameOfFarmer: String,
    val cityName: String,
    val date: String,
    val billNo: String,
    var totalPayAbleAmount: String,
    var cardData: List<CardData> = listOf(),
)

data class CardData(
    var id:String,
    var quantity: String = "",
    var weight: String = "",
    var price: String = "",
    var totalPrice: String = "",
    var typeOfProduct: String = "कांदा"
)



