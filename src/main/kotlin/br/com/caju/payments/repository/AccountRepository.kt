package br.com.caju.payments.repository

import br.com.caju.payments.repository.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<AccountEntity, String>
