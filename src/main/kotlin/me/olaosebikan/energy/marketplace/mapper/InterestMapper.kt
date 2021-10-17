package me.olaosebikan.energy.marketplace.mapper

import me.olaosebikan.energy.marketplace.dto.CertificateInterestDTO
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import org.springframework.stereotype.Component

@Component
class InterestMapper : Mapper<CertificateInterestDTO, CertificateInterest> {

    override fun fromEntity(entity: CertificateInterest): CertificateInterestDTO =
        CertificateInterestDTO(
            entity.energySource,
            entity.quantity,
            entity.buyerId
        )

     override fun toEntity(dto: CertificateInterestDTO): CertificateInterest =
         CertificateInterest(
             dto.energySource,
             dto.quantity,
             dto.buyerId,
             ""
         )
}