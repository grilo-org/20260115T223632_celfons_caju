package br.com.caju.payments.service

import br.com.caju.payments.domain.Account
import br.com.caju.payments.domain.Transaction
import br.com.caju.payments.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import br.com.caju.payments.domain.ProcessingException
import br.com.caju.payments.repository.MerchantRepository
import br.com.caju.payments.repository.TransactionRepository
import br.com.caju.payments.repository.entity.AccountEntity
import br.com.caju.payments.repository.entity.TransactionEntity
import jakarta.transaction.Transactional
import java.util.UUID

@Service
class AuthorizerService(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val merchantRepository: MerchantRepository,
    @Autowired private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun simpleAuthorizer(transaction: Transaction) {
        if(validIdempotency(transaction.id)) {
            accountRepository.findById(transaction.accountId)
                .orElseThrow { ProcessingException() }
                .fromEntity()
                .debit(transaction.mcc, transaction.amount)
                .also {
                    accountRepository.saveAndFlush(it.toEntity())
                }
            transactionRepository.save(TransactionEntity(transaction.id))
        }
    }

    @Transactional
    fun fallbackAuthorizer(transaction: Transaction) {
        if(validIdempotency(transaction.id)) {
            accountRepository.findById(transaction.accountId)
                .orElseThrow { ProcessingException() }
                .fromEntity()
                .debitWithFallback(transaction.mcc, transaction.amount)
                .also {
                    accountRepository.saveAndFlush(it.toEntity())
                }
            transactionRepository.save(TransactionEntity(transaction.id))
        }
    }

    @Transactional
    fun merchantDependentAuthorizer(transaction: Transaction) {
        if (validIdempotency(transaction.id)) {

            val mcc = merchantRepository.findByName(transaction.merchant)
                .takeIf { it.isPresent }?.get()?.code ?: transaction.mcc

            accountRepository.findById(transaction.accountId)
                .orElseThrow { ProcessingException() }
                .fromEntity()
                .debitWithFallback(mcc, transaction.amount)
                .also {
                    accountRepository.saveAndFlush(AccountEntity(it.id, it.foodBalance, it.mealBalance, it.cashBalance))
                }
            transactionRepository.save(TransactionEntity(transaction.id))
        }
    }

    private fun validIdempotency(id: UUID) = !transactionRepository.existsById(id)

    fun Account.toEntity() = AccountEntity(
        this.id,
        this.foodBalance,
        this.mealBalance,
        this.cashBalance
    )

}