package br.com.caju.payments.controller.request

import br.com.caju.payments.domain.Transaction
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.util.UUID

data class TransactionRequest(
    val id: UUID? = UUID.randomUUID(),
    @NotEmpty val accountId: String,
    @NotNull val amount: BigDecimal,
    val merchant: String,
    @NotEmpty @Size(min = 4, max = 4) val mcc: String
) {

    fun toDomain() = Transaction(
        this.id!!,
        this.accountId,
        this.amount,
        this.merchant,
        this.mcc
    )

}