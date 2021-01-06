package com.uclgroupgh.momoapitesting.models

import androidx.databinding.Bindable

data class PaymentReceipt(
    var selectedNumbers: String = "",
    var gamePlayed: String = "",
    var amountStaked: String = "",
    var receiptId: String = "",
    var playerNumber: String = "",
    var possibleWinnings: String = "0",
    var reference: String = ""
)
