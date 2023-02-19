package com.commerce.client

import com.commerce.grpc.TransactionRequest
import com.commerce.grpc.TransactionResponse
import com.commerce.util.generateTransactionRequest
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("transaction-client")
class CommerceTransactionClient : AbstractCommerceClient<TransactionRequest, TransactionResponse>() {

    override fun generateRequest(): TransactionRequest {
        return generateTransactionRequest()
    }

    override fun getResponse(request: TransactionRequest): TransactionResponse {
        return stub.transaction(request)
    }
}
