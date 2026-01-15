package br.com.caju.payments.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountTest {

    @Test
    fun `debit should reduce the correct balance`() {
        val account = Account(id = "123", foodBalance = BigDecimal(100), mealBalance = BigDecimal(50), cashBalance = BigDecimal(200))
        account.debit("5411", BigDecimal(30))
        assertEquals(BigDecimal(70), account.foodBalance)
    }

    @Test
    fun `debit should throw InsufficientBalanceException when balance is insufficient`() {
        val account = Account(id = "1", foodBalance = BigDecimal(100), mealBalance = BigDecimal(50), cashBalance = BigDecimal(200))
        assertThrows(InsufficientBalanceException::class.java) {
            account.debit("5411", BigDecimal(130))
        }
    }

    @Test
    fun `debitWithFallback should reduce the correct balance`() {
        val account = Account(id = "1", foodBalance = BigDecimal(100), mealBalance = BigDecimal(50), cashBalance = BigDecimal(200))
        account.debitWithFallback("5411", BigDecimal(30))
        assertEquals(BigDecimal(70), account.foodBalance)
    }

    @Test
    fun `debitWithFallback should use cash balance when category balance is insufficient`() {
        val account = Account(id = "1", foodBalance = BigDecimal(100), mealBalance = BigDecimal(50), cashBalance = BigDecimal(200))
        account.debitWithFallback("5411", BigDecimal(130))
        assertEquals(BigDecimal.ZERO, account.foodBalance)
        assertEquals(BigDecimal(170), account.cashBalance)
    }

    @Test
    fun `debitWithFallback should throw InsufficientBalanceException when total balance is insufficient`() {
        val account = Account(id = "1", foodBalance = BigDecimal(100), mealBalance = BigDecimal(50), cashBalance = BigDecimal(200))
        assertThrows(InsufficientBalanceException::class.java) {
            account.debitWithFallback("5411", BigDecimal(350))
        }
    }
}