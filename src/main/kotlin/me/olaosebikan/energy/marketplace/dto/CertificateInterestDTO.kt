package me.olaosebikan.energy.marketplace.dto

import javax.persistence.EnumType
import javax.persistence.Enumerated

data class CertificateInterestDTO (
    @Enumerated(EnumType.STRING)
    var energySource : EnergySource,
    val quantity : Int,
    val buyerId: String
)