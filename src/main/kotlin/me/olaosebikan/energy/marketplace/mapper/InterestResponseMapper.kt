package me.olaosebikan.energy.marketplace.mapper

import me.olaosebikan.energy.marketplace.dto.CertificateInterestResponseDTO
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import org.springframework.stereotype.Component

@Component
class InterestResponseMapper : Mapper<CertificateInterestResponseDTO, CertificateInterest> {

    override fun fromEntity(entity: CertificateInterest): CertificateInterestResponseDTO =
        CertificateInterestResponseDTO(
            entity.id,
            entity.energySource,
            entity.quantity,
            entity.buyerId,
            entity.sellerId,
            entity.status
        )

    override fun toEntity(dto: CertificateInterestResponseDTO): CertificateInterest =
        CertificateInterest(
            dto.energySource,
            dto.quantity,
            dto.buyerId,
            dto.sellerId
        )


}