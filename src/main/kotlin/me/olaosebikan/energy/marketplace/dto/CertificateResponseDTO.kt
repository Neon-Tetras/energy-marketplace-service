package me.olaosebikan.energy.marketplace.dto

import java.time.LocalDate
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated

class CertificateResponseDTO (
    val  id : Long? = null,
    val ownerId: String,
    @Enumerated(EnumType.STRING)
    var energySource : EnergySource,
    val quantity : Int,
    val issuer : String,
    val issueDate : String
)