package com.commerce.util

import com.commerce.grpc.*


fun TransactionRequest.print(): String {
    return "TransactionRequest(customerId=$customerId, price=$price, priceModifier=$priceModifier, " +
            "paymentMethod='$paymentMethod', dateTime=$dateTime, additionalItem=" + additionalItem.print()+ ")"
}

fun AdditionalItem.print(): String {
    return "AdditionalItem(last4=$last4, courier=$courier, bankItem=" + bankItem.print() + ")"
}

fun BankItem.print(): String {
    return "BankItem(bankName='$bankName', accountNumber=$accountNumber, chequeNumber=$chequeNumber)"
}

fun TransactionResponse.print(): String {
    return "TransactionResponse(finalPrice=$finalPrice, points=$points)"
}

fun TransactionReportRequest.print(): String {
    return "TransactionReportRequest(startDateTime=$startDateTime, endDateTime=$endDateTime)"
}

fun TransactionReportResponse.print(): String {
    return "TransactionReportResponse(salesList.size()=" + salesList.size + ")"
}