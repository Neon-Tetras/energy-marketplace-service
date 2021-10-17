package me.olaosebikan.energy.marketplace.entity

import me.olaosebikan.energy.marketplace.dto.EnergySource
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table
data class Certificate (
     var ownerId: String,
     @Enumerated(EnumType.STRING)
     var energySource : EnergySource,
     var quantity : Int,
     var issuer : String,
     var issueDate : LocalDate
)
{
     @Id @GeneratedValue(strategy= GenerationType.AUTO)
     var id : Long? = null
}

