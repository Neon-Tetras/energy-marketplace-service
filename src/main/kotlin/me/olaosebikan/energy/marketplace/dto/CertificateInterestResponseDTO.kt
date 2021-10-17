package me.olaosebikan.energy.marketplace.dto

import javax.persistence.EnumType
import javax.persistence.Enumerated

data class CertificateInterestResponseDTO (
    var id : Long? = null,
    @Enumerated(EnumType.STRING)
    var energySource : EnergySource,
    var quantity : Int,
    var buyerId: String,
    var sellerId: String,
    @Enumerated(EnumType.STRING)
    var status: InterestStatus
)