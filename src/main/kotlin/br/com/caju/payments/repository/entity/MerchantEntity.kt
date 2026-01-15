package br.com.caju.payments.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "merchant")
data class MerchantEntity(
    @Id val id: UUID? = UUID.randomUUID(),
    val code: String,
    val name: String
) : BaseEntity()