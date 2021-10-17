package me.olaosebikan.energy.marketplace.repository

import me.olaosebikan.energy.marketplace.entity.Certificate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificateRepository : JpaRepository<Certificate, Long> {
    fun findByOwnerId (ownerId: String): MutableList <Certificate>
}