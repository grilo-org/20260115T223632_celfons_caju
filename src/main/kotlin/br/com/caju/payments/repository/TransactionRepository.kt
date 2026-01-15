package br.com.caju.payments.repository

import br.com.caju.payments.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, UUID>
