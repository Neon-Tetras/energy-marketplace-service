package me.olaosebikan.energy.marketplace.service

import me.olaosebikan.energy.marketplace.dto.*

interface CertificateService {
    fun createCertificate (certificateDTO : CertificateDTO) : CertificateResponseDTO;
    fun getCertificatesByOwnerId (ownerId: String) : List<CertificateResponseDTO>;
    fun createInterest (certificateInterestDTO: CertificateInterestDTO) : CertificateInterestResponseDTO;
    fun getAllOpenInterests () : List<CertificateInterestResponseDTO>;
    fun matchInterest (matchInterestDTO : MatchInterestDTO) : MatchInterestResponseDTO;
    fun getInterestsByBuyerId (buyerId : String) : List<CertificateInterestResponseDTO>;
}