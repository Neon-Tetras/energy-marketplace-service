package me.olaosebikan.energy.marketplace.mapper

import me.olaosebikan.energy.marketplace.dto.MatchInterestResponseDTO
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import org.springframework.stereotype.Component

@Component
class MatchInterestMapper : Mapper<MatchInterestResponseDTO, CertificateInterest> {

    override fun fromEntity(entity: CertificateInterest): MatchInterestResponseDTO =
        MatchInterestResponseDTO(
            entity.id,
            entity.energySource,
            entity.quantity,
            entity.buyerId,
            entity.sellerId,
            entity.status
        )

    override fun toEntity(dto: MatchInterestResponseDTO): CertificateInterest =
        CertificateInterest(
            dto.energySource,
            dto.quantity,
            dto.buyerId,
            dto.sellerId
        )
}