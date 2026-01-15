package br.com.caju.payments.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "transaction")
data class TransactionEntity(@Id val id: UUID) : BaseEntity()
