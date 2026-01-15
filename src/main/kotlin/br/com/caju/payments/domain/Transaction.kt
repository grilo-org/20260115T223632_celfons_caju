package br.com.caju.payments.domain

import java.math.BigDecimal
import java.util.UUID

data class Transaction(
    val id: UUID,
    val accountId: String,
    val amount: BigDecimal,
    val merchant: String,
    val mcc: String
)
