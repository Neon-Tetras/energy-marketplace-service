package me.olaosebikan.energy.marketplace.service

import me.olaosebikan.energy.marketplace.dto.*
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import me.olaosebikan.energy.marketplace.exception.BusinessException
import me.olaosebikan.energy.marketplace.exception.RecordNotFoundException
import me.olaosebikan.energy.marketplace.mapper.*
import me.olaosebikan.energy.marketplace.repository.CertificateInterestRepository
import me.olaosebikan.energy.marketplace.repository.CertificateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CertificateServiceImpl : CertificateService {

    @Autowired
    lateinit var certificateRepository: CertificateRepository;

    @Autowired
    lateinit var certificateInterestRepository: CertificateInterestRepository;

    @Autowired
    lateinit var certificateMapper: CertificateMapper;

    @Autowired
    lateinit var certificateResponseMapper: CertificateResponseMapper;

    @Autowired
    lateinit var interestMapper: InterestMapper;

    @Autowired
    lateinit var matchInterestMapper: MatchInterestMapper;

    @Autowired
    lateinit var interestResponseMapper: InterestResponseMapper;


    override fun createCertificate(certificateDTO: CertificateDTO): CertificateResponseDTO {
        val certificate = certificateMapper.toEntity(certificateDTO);
        return certificateResponseMapper.fromEntity(certificateRepository.save(certificate));
    }

    override fun getCertificatesByOwnerId(ownerId: String): List<CertificateResponseDTO> {
        var certificateList = certificateRepository.findByOwnerId(ownerId);
        if (certificateList.isEmpty()) {
            throw RecordNotFoundException("Record not found");
        }
        return certificateList.map {
            certificateResponseMapper.fromEntity(it)
        };
    }

    override fun createInterest(certificateInterestDTO: CertificateInterestDTO): CertificateInterestResponseDTO {
        var certificateInterest = interestMapper.toEntity(certificateInterestDTO);
        certificateInterest.status = InterestStatus.OPEN;
        return interestResponseMapper.fromEntity(certificateInterestRepository.save(certificateInterest));
    }

    override fun getAllOpenInterests(): List<CertificateInterestResponseDTO> {
        var interestList = certificateInterestRepository.findAllOpenInterest();
        return interestList.map {
            interestResponseMapper.fromEntity(it)
        }
    }

    override fun matchInterest(matchInterestDTO: MatchInterestDTO): MatchInterestResponseDTO {

        var certificateInterest = certificateInterestRepository.getById(matchInterestDTO.interestId);

        if (certificateInterest.status != InterestStatus.OPEN) {
            throw BusinessException("Interest not open");
        } else {
            matchCertificate(matchInterestDTO, certificateInterest)
        }

        ///closed interest
        certificateInterest.sellerId = matchInterestDTO.sellerId;
        certificateInterest.status = InterestStatus.CLOSED;
        return matchInterestMapper.fromEntity(certificateInterestRepository.save(certificateInterest));
    }

    override fun getInterestsByBuyerId(buyerId: String): List<CertificateInterestResponseDTO> {
        var interestList = certificateInterestRepository.findByBuyerId(buyerId);

        if (interestList.isEmpty()) {
            throw RecordNotFoundException("Record not found");
        }
        return interestList.map {
            interestResponseMapper.fromEntity(it)
        }
    }

    fun matchCertificate(matchInterestDTO: MatchInterestDTO, certificateInterest: CertificateInterest) {
        var totalQuantity = 0;

        matchInterestDTO.certificateIds.map {
            var certificate = certificateRepository.getById(it)
            if (certificate == null) {
                RecordNotFoundException("Certificate with id $it not found")
            }
            if (certificate.energySource != certificateInterest.energySource) {
                throw BusinessException("Solar source did not match");
            }
            totalQuantity += certificate.quantity;
        }
        if (totalQuantity == certificateInterest.quantity) {
            changeCertificateOwner(matchInterestDTO, certificateInterest)
        } else {
            throw BusinessException("Quantity did not match");
        }
    }

    fun changeCertificateOwner(matchInterestDTO: MatchInterestDTO, certificateInterest: CertificateInterest) {
        matchInterestDTO.certificateIds.map {
            var certificate = certificateRepository.getById(it)
            //change owner
            certificate.ownerId = certificateInterest.buyerId;
            certificateRepository.save(certificate);
        }
    }

}