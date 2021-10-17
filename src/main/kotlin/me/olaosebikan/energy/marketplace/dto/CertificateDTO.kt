package me.olaosebikan.energy.marketplace.dto


import com.fasterxml.jackson.annotation.JsonFormat
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class CertificateDTO (
    val ownerId: String,
    @Enumerated(EnumType.STRING)
    var energySource : EnergySource,
    val quantity : Int,
    val issuer : String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val issueDate : String
)