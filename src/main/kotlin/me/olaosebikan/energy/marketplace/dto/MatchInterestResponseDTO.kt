package me.olaosebikan.energy.marketplace.dto

import javax.persistence.EnumType
import javax.persistence.Enumerated

data class MatchInterestResponseDTO (
    var id : Long? = 0,
    @Enumerated(EnumType.STRING)
    var energySource : EnergySource,
    var quantity : Int,
    var buyerId: String,
    var sellerId: String,
    @Enumerated(EnumType.STRING)
    var status: InterestStatus
)