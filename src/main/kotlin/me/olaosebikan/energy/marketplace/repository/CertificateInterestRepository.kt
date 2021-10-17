package me.olaosebikan.energy.marketplace.repository

import me.olaosebikan.energy.marketplace.entity.Certificate
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CertificateInterestRepository : JpaRepository<CertificateInterest, Long> {
    @Query("select c from CertificateInterest c where c.status = 'OPEN'")
    fun findAllOpenInterest (): MutableList <CertificateInterest>
    fun findByBuyerId (buyerId: String): MutableList <CertificateInterest>
}