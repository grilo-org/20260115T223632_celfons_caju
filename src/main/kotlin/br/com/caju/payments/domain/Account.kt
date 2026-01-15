package br.com.caju.payments.domain

import java.math.BigDecimal

data class Account(
    val id: String,
    var foodBalance: BigDecimal = BigDecimal.ZERO,
    var mealBalance: BigDecimal = BigDecimal.ZERO,
    var cashBalance: BigDecimal = BigDecimal.ZERO
) {

    fun debit(mcc: String, amount: BigDecimal): Account {
        val category = MCC.from(mcc)
        balances()[category]?.let {
            when {
                it >= amount -> updateBalance(category, it - amount)
                else -> throw InsufficientBalanceException()
            }
        } ?: throw InsufficientBalanceException()
        return this
    }

    fun debitWithFallback(mcc: String, amount: BigDecimal): Account {
        val category = MCC.from(mcc)
        val balance = balances()[category] ?: throw InsufficientBalanceException()

        when {
            balance >= amount -> updateBalance(category, balance - amount)
            category != MCC.CASH && (balance + cashBalance) >= amount -> {
                val excessAmount = amount - balance
                updateBalance(category, BigDecimal.ZERO)
                cashBalance -= excessAmount
            }
            else -> throw InsufficientBalanceException()
        }
        return this
    }

    private fun balances() = mapOf(
        MCC.FOOD to foodBalance,
        MCC.MEAL to mealBalance,
        MCC.CASH to cashBalance
    )

    private fun updateBalance(category: MCC, newBalance: BigDecimal) {
        when (category) {
            MCC.FOOD -> foodBalance = newBalance
            MCC.MEAL -> mealBalance = newBalance
            MCC.CASH -> cashBalance = newBalance
        }
    }
}
