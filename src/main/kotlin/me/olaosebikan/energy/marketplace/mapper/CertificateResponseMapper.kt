package me.olaosebikan.energy.marketplace.mapper

import me.olaosebikan.energy.marketplace.dto.CertificateDTO
import me.olaosebikan.energy.marketplace.dto.CertificateResponseDTO
import me.olaosebikan.energy.marketplace.entity.Certificate
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class CertificateResponseMapper : Mapper<CertificateResponseDTO, Certificate>  {

    override fun fromEntity(entity: Certificate): CertificateResponseDTO =
        CertificateResponseDTO(
            entity.id,
            entity.ownerId,
            entity.energySource,
            entity.quantity,
            entity.issuer,
            entity.issueDate.toString()
        )

    override fun toEntity(dto: CertificateResponseDTO): Certificate =
        Certificate(
            dto.ownerId,
            dto.energySource,
            dto.quantity,
            dto.issuer,
            LocalDate.parse(dto.issueDate, DateTimeFormatter.ISO_DATE)
        )

}