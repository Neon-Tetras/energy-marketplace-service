package me.olaosebikan.energy.marketplace.service

import me.olaosebikan.energy.marketplace.dto.*
import me.olaosebikan.energy.marketplace.entity.Certificate
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import me.olaosebikan.energy.marketplace.exception.BusinessException
import me.olaosebikan.energy.marketplace.repository.CertificateInterestRepository
import me.olaosebikan.energy.marketplace.repository.CertificateRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CertificateServiceTest {

    @Autowired
    lateinit var certificateServiceImpl: CertificateServiceImpl;

    @MockBean
    lateinit var certificateRepository: CertificateRepository;

    @MockBean
    lateinit var certificateInterestRepository: CertificateInterestRepository;

    @Test
    @DisplayName("should create new certificate")
    fun createCertificate_shouldNewCertificate() {
        //given
        var certificateDTO =
            CertificateDTO("1001", EnergySource.SOLAR, 100, "First Dutch", "2021-10-17")
        val mockCertificate = Certificate("1001", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        `when`(certificateRepository.save(any())).thenReturn(mockCertificate);

        //when
        var createdCertificate = certificateServiceImpl.createCertificate(certificateDTO)

        //then
        assertThat(certificateDTO.ownerId).isEqualTo(createdCertificate.ownerId)
        assertThat(certificateDTO.energySource).isEqualTo(createdCertificate.energySource)
        assertThat(certificateDTO.quantity).isEqualTo(createdCertificate.quantity)
        assertThat(certificateDTO.issuer).isEqualTo(createdCertificate.issuer)
        assertThat(certificateDTO.issueDate).isEqualTo(createdCertificate.issueDate)
    }

    @Test
    @DisplayName("should return owners certificates")
    fun getAllCertificateByOwnerId_shouldReturnOwnerCertificates() {
        //given
        val ownerId: String = "1001";
        val mockCertificates =
            mutableListOf(Certificate(ownerId, EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17")))
        `when`(certificateRepository.findByOwnerId(ownerId)).thenReturn(mockCertificates)

        //when
        val certificates = certificateServiceImpl.getCertificatesByOwnerId(ownerId)

        //then
        assertThat(certificates).hasSize(1);
        assertThat(certificates[0].ownerId).isEqualTo(ownerId);
        assertThat(certificates[0].energySource).isEqualTo(EnergySource.SOLAR);
        assertThat(certificates[0].quantity).isEqualTo(100);
        assertThat(certificates[0].issuer).isEqualTo("First Dutch");
        assertThat(certificates[0].issueDate).isEqualTo("2021-10-17");
    }

    @Test
    @DisplayName("should create new certificates interest")
    fun createNewCertificateInterest_shouldReturnCertificatesInterest() {
        //given
        var interest = CertificateInterestDTO(EnergySource.SOLAR, 100, "2002")
        var mockedInterest = CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)
        `when`(certificateInterestRepository.save(any())).thenReturn(mockedInterest)

        //when
        var createdInterest = certificateServiceImpl.createInterest(interest);

        //then
        assertThat(createdInterest.energySource).isEqualTo(interest.energySource);
        assertThat(createdInterest.quantity).isEqualTo(interest.quantity);
        assertThat(createdInterest.buyerId).isEqualTo(interest.buyerId);
    }

    @Test
    @DisplayName("should return all open interest")
    fun getAllOpenInterests_shouldReturnOpenInterests() {
        //given
        val mockCertificateInterests =
            mutableListOf(CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN))
        `when`(certificateInterestRepository.findAllOpenInterest()).thenReturn(mockCertificateInterests)

        //when
        var interestList = certificateServiceImpl.getAllOpenInterests();

        //then
        assertThat(interestList).hasSize(1)
        assertThat(interestList[0].energySource).isEqualTo(EnergySource.SOLAR);
        assertThat(interestList[0].quantity).isEqualTo(100);
        assertThat(interestList[0].buyerId).isEqualTo("2002");
        assertThat(interestList[0].status).isEqualTo(InterestStatus.OPEN);
    }

    @Test
    @DisplayName("should return all buyer open interest by buyer Id")
    fun getAllBuyerOpenInterestsByBuyerId_shouldReturnOpenInterests() {
        //given
        val buyerId = "2002"
        val mockCertificateInterests =
            mutableListOf(CertificateInterest(EnergySource.SOLAR, 100, buyerId, "", InterestStatus.OPEN))
        `when`(certificateInterestRepository.findByBuyerId(anyString())).thenReturn(mockCertificateInterests)

        //when
        var interestList = certificateServiceImpl.getInterestsByBuyerId(buyerId)

        //then
        assertThat(interestList).hasSize(1)
        assertThat(interestList[0].energySource).isEqualTo(EnergySource.SOLAR);
        assertThat(interestList[0].quantity).isEqualTo(100);
        assertThat(interestList[0].buyerId).isEqualTo(buyerId);
        assertThat(interestList[0].status).isEqualTo(InterestStatus.OPEN);
    }


    @Test
    @DisplayName("should change owner id ")
    fun changeOwnerId_shouldVerifyRepositoryCall() {
        //given
        var matchInterestDTO = MatchInterestDTO(1L, "1001", arrayOf(1L))
        var certificateInterest = CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)
        var mockCertificate = Certificate("2002", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))

        `when`(certificateRepository.getById(anyLong())).thenReturn(mockCertificate)
        `when`(certificateRepository.save(any())).thenReturn(mockCertificate);

        //when
        certificateServiceImpl.changeCertificateOwner(matchInterestDTO, certificateInterest);

        //then
        verify(certificateRepository, times(1)).getById(1L);
        verify(certificateRepository, times(1)).save(mockCertificate);
    }

    @Test
    @DisplayName("should match interest")
    fun matchCertificateInterest_shouldVerify() {
        //given
        var matchInterestDTO = MatchInterestDTO(1L, "1001", arrayOf(1L))
        var certificateInterest = CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)
        var mockCertificate = Certificate("2002", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockCertificateInterest = CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)

        `when`(certificateRepository.getById(anyLong())).thenReturn(mockCertificate)

        //when
        certificateServiceImpl.matchCertificate(matchInterestDTO, certificateInterest);

        //then
        verify(certificateRepository, times(2)).getById(1L);
    }

    @Test
    @DisplayName("should return matched interest")
    fun matchCertificateInterest_shouldReturnClosedInterest() {
        ///given
        var matchInterestDTO = MatchInterestDTO(1L, "1001", arrayOf(1L))
        var mockCertificateInterest = CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)
        var mockCertificate = Certificate("1001", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockSavedCertificate =
            Certificate("2002", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockSavedCertificateInterest =
            CertificateInterest(EnergySource.SOLAR, 100, "2002", "1001", InterestStatus.CLOSED)

        `when`(certificateInterestRepository.getById(anyLong())).thenReturn(mockCertificateInterest)
        `when`(certificateRepository.getById(anyLong())).thenReturn(mockCertificate)
        `when`(certificateRepository.save(any())).thenReturn(mockSavedCertificate)
        `when`(certificateInterestRepository.save(any())).thenReturn(mockSavedCertificateInterest)

        //when
        var matchedInterest = certificateServiceImpl.matchInterest(matchInterestDTO);

        //then
        assertThat(matchedInterest).isNotNull
        assertThat(matchedInterest.energySource).isEqualTo(EnergySource.SOLAR);
        assertThat(matchedInterest.quantity).isEqualTo(100);
        assertThat(matchedInterest.buyerId).isEqualTo("2002");
        assertThat(matchedInterest.sellerId).isEqualTo(matchInterestDTO.sellerId);
        assertThat(matchedInterest.status).isEqualTo(InterestStatus.CLOSED);
    }

    @Test
    @DisplayName("should return energy source exception")
    fun matchCertificateInterestEnergySourceException_shouldReturnEnergySourceNotMatchException() {
        ///given
        var matchInterestDTO = MatchInterestDTO(1L, "1001", arrayOf(1L))
        var mockCertificateInterest = CertificateInterest(EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)
        var mockCertificate = Certificate("1001", EnergySource.WIND, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockSavedCertificate =
            Certificate("2002", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockSavedCertificateInterest =
            CertificateInterest(EnergySource.SOLAR, 100, "2002", "1001", InterestStatus.CLOSED)

        `when`(certificateInterestRepository.getById(anyLong())).thenReturn(mockCertificateInterest)
        `when`(certificateRepository.getById(anyLong())).thenReturn(mockCertificate)
        `when`(certificateRepository.save(any())).thenReturn(mockSavedCertificate)
        `when`(certificateInterestRepository.save(any())).thenReturn(mockSavedCertificateInterest)

        // when
        val thrown: BusinessException = assertThrows(
            BusinessException::class.java
        ) { certificateServiceImpl.matchInterest(matchInterestDTO) }

        //then
        assertTrue(thrown.message!!.contains("Solar source did not match"))
    }


    @Test
    @DisplayName("should return quantity not match exception")
    fun matchCertificateInterestQuantityException_shouldReturnQuantityNotMatchException() {
        ///given
        var matchInterestDTO = MatchInterestDTO(1L, "1001", arrayOf(1L))
        var mockCertificateInterest = CertificateInterest(EnergySource.SOLAR, 5000, "2002", "", InterestStatus.OPEN)
        var mockCertificate = Certificate("1001", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockSavedCertificate =
            Certificate("2002", EnergySource.SOLAR, 100, "First Dutch", LocalDate.parse("2021-10-17"))
        var mockSavedCertificateInterest =
            CertificateInterest(EnergySource.SOLAR, 100, "2002", "1001", InterestStatus.CLOSED)

        `when`(certificateInterestRepository.getById(anyLong())).thenReturn(mockCertificateInterest)
        `when`(certificateRepository.getById(anyLong())).thenReturn(mockCertificate)
        `when`(certificateRepository.save(any())).thenReturn(mockSavedCertificate)
        `when`(certificateInterestRepository.save(any())).thenReturn(mockSavedCertificateInterest)

        // when
        val thrown: BusinessException = assertThrows(
            BusinessException::class.java
        ) { certificateServiceImpl.matchInterest(matchInterestDTO) }

        //then
        assertTrue(thrown.message!!.contains("Quantity did not match"))
    }
}