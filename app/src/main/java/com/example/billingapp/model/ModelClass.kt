package com.example.billingapp.model

data class BillingModelClass(
    var billingDetails: ArrayList<BillingDetails>,
    var cardData: List<CardData> = listOf(),
    var expenseModelClass: List<ExpenseModelClass>,
)

data class ExpenseModelClass(
    var aadat: String,
    var hamali: String,
    var tolai: String,
    var varai: String,
    var bharai: String,
    var travelingExpense: String,
    var uchal: String,
    var bardhana: String,
    var otherExpense: String,
    var id: String,
)

data class CardData(
    var id:String,
    var quantity: String = "",
    var weight: String = "",
    var price: String = "",
    var totalPrice: String = "",
    var typeOfProduct: String = "कांदा"
)

data class BillingDetails(
    val id: String,
    val nameOfFarmer: String,
    val cityName: String,
    val date: String,
    val billNo: String,
    var totalPayAbleAmount: String,
)

