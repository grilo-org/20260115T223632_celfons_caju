package br.com.caju.payments.repository.entity

import br.com.caju.payments.domain.Account
import java.math.BigDecimal
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id val id: String,
    val foodBalance: BigDecimal,
    val mealBalance: BigDecimal,
    val cashBalance: BigDecimal
) : BaseEntity() {

    fun fromEntity() = Account(
        this.id,
        this.foodBalance,
        this.mealBalance,
        this.cashBalance
    )
    
}