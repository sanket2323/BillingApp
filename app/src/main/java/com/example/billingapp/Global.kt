package com.example.billingapp

import com.example.billingapp.model.BillingDetails
import com.example.billingapp.model.BillingModelClass

object Global {
    var listOfAdminIdAndPassword = HashMap<String,String>()
    var adminId: String? = null
    var currentModel: BillingModelClass? = null
}