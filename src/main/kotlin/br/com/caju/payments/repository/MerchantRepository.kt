package br.com.caju.payments.repository

import br.com.caju.payments.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MerchantRepository : JpaRepository<MerchantEntity, UUID> {

    fun findByName(name: String) : Optional<MerchantEntity>

}
