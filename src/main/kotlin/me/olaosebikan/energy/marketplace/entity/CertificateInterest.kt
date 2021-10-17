package me.olaosebikan.energy.marketplace.entity

import me.olaosebikan.energy.marketplace.dto.EnergySource
import me.olaosebikan.energy.marketplace.dto.InterestStatus
import javax.persistence.*

@Entity
@Table
data class CertificateInterest (
    @Enumerated(EnumType.STRING)
    var energySource : EnergySource,
    var quantity : Int,
    var buyerId: String,
    var sellerId: String,
    @Enumerated(EnumType.STRING)
    var status: InterestStatus = InterestStatus.OPEN
)
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id : Long? = null;
}