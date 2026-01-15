package br.com.caju.payments.service

import br.com.caju.payments.domain.Transaction
import br.com.caju.payments.domain.ProcessingException
import br.com.caju.payments.repository.AccountRepository
import br.com.caju.payments.repository.MerchantRepository
import br.com.caju.payments.repository.TransactionRepository
import br.com.caju.payments.repository.entity.AccountEntity
import br.com.caju.payments.repository.entity.TransactionEntity
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthorizerServiceTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var merchantRepository: MerchantRepository

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    private lateinit var authorizerService: AuthorizerService


    @Test
    fun `authorizer should dont process transaction`() {

        val transaction = buildTransaction()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(true)

        authorizerService.simpleAuthorizer(transaction)

        verifyNoInteractions(accountRepository)

    }

    @Test
    fun `simpleAuthorizer should process transaction successfully`() {

        val transaction = buildTransaction()

        val accountEntity = buildAccount()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(false)
        `when`(accountRepository.findById("123")).thenReturn(Optional.of(accountEntity))

        authorizerService.simpleAuthorizer(transaction)

        verify(accountRepository).saveAndFlush(any(AccountEntity::class.java))
        verify(transactionRepository).save(any(TransactionEntity::class.java))

    }

    @Test
    fun `simpleAuthorizer should process transaction unsuccessfully`() {

        val transaction = buildTransaction()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(false)
        `when`(accountRepository.findById("123")).thenReturn(Optional.empty())

        assertThrows(ProcessingException::class.java) {
            authorizerService.simpleAuthorizer(transaction)
        }

    }

    @Test
    fun `fallbackAuthorizer should process transaction successfully`() {

        val transaction = buildTransaction()

        val accountEntity = buildAccount()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(false)
        `when`(accountRepository.findById("123")).thenReturn(Optional.of(accountEntity))

        authorizerService.fallbackAuthorizer(transaction)

        verify(accountRepository).saveAndFlush(any(AccountEntity::class.java))
        verify(transactionRepository).save(any(TransactionEntity::class.java))

    }

    @Test
    fun `fallbackAuthorizer should process transaction unsuccessfully`() {

        val transaction = buildTransaction()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(false)
        `when`(accountRepository.findById("123")).thenReturn(Optional.empty())

        assertThrows(ProcessingException::class.java) {
            authorizerService.fallbackAuthorizer(transaction)
        }

    }

    @Test
    fun `merchantDependentAuthorizer should process transaction successfully`() {

        val transaction = buildTransaction()

        val accountEntity = buildAccount()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(false)
        `when`(accountRepository.findById("123")).thenReturn(Optional.of(accountEntity))

        authorizerService.merchantDependentAuthorizer(transaction)

        verify(accountRepository).saveAndFlush(any(AccountEntity::class.java))
        verify(transactionRepository).save(any(TransactionEntity::class.java))

    }

    @Test
    fun `merchantDependentAuthorizer should process transaction unsuccessfully`() {

        val transaction = buildTransaction()

        `when`(transactionRepository.existsById(transaction.id)).thenReturn(false)
        `when`(accountRepository.findById("123")).thenReturn(Optional.empty())

        assertThrows(ProcessingException::class.java) {
            authorizerService.merchantDependentAuthorizer(transaction)
        }

    }

    private fun buildAccount() = AccountEntity(
        id = "123",
        foodBalance = BigDecimal.TWO,
        mealBalance = BigDecimal.TWO,
        cashBalance = BigDecimal.TWO
    )

    private fun buildTransaction() = Transaction(
        id = UUID.randomUUID(),
        accountId = "123",
        mcc = "5811",
        amount = BigDecimal.ONE,
        merchant = "Test Merchant"
    )

}
