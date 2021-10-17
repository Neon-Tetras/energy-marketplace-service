package me.olaosebikan.energy.marketplace.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.olaosebikan.energy.marketplace.dto.*
import me.olaosebikan.energy.marketplace.service.CertificateService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class CertificateControllerTest {

    @MockBean
    lateinit var certificateService: CertificateService;

    @Autowired
    lateinit var mockMvc: MockMvc;

    @Test
    @DisplayName("should create new certificate")
    fun createCertificate_shouldNewCertificate() {
        //given
        var certificateDTO =
            CertificateDTO("1001", EnergySource.SOLAR, 100, "First Dutch", "2021-10-17")
        val mockCertificate =
            CertificateResponseDTO(1L, "1001", EnergySource.SOLAR, 100, "First Dutch", "2021-10-17")
        `when`(certificateService.createCertificate(certificateDTO)).thenReturn(mockCertificate);

        val objectMapper = ObjectMapper()
        val jsonRequest = objectMapper.writeValueAsString(certificateDTO)

        //when //then
        mockMvc.perform(
            post("/api/v1/certificates/")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON) )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").isNotEmpty)
            .andExpect(jsonPath("energySource").value(certificateDTO.energySource.toString()))
            .andExpect(jsonPath("issueDate").value(certificateDTO.issueDate))
            .andExpect(jsonPath("issuer").value(certificateDTO.issuer))
            .andExpect(jsonPath("ownerId").value(certificateDTO.ownerId))
            .andExpect(jsonPath("quantity").value(certificateDTO.quantity));

    }

    @Test
    @DisplayName("should return owner certificates by owner Id")
    fun getAllOwnerCertificatesByOwnerId_shouldReturnCertificates() {
        //given
        val ownerId: String = "1001";
        val mockCertificates = mutableListOf(
            CertificateResponseDTO(1L, ownerId, EnergySource.SOLAR, 100, "First Dutch", "2021-10-14")
        )
        Mockito.`when`(certificateService.getCertificatesByOwnerId(ownerId)).thenReturn(mockCertificates)

        //when  //then
        mockMvc.perform(
            get("/api/v1/certificates/owner/{ownerId}", ownerId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].ownerId").value(ownerId))
            .andExpect(jsonPath("$[0].energySource").value(EnergySource.SOLAR.toString()))
            .andExpect(jsonPath("$[0].quantity").value(100))
            .andExpect(jsonPath("$[0].issuer").value("First Dutch"))
            .andExpect(jsonPath("$[0].issueDate").value("2021-10-14"))
    }

    @Test
    @DisplayName("should create new certificates interest")
    fun createNewCertificateInterest_shouldReturnCertificatesInterest() {
        //given
        var interest = CertificateInterestDTO(EnergySource.SOLAR, 100, "2002")
        var mockedInterest =
            CertificateInterestResponseDTO(1L, EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN)
        `when`(certificateService.createInterest(interest)).thenReturn(mockedInterest)

        val objectMapper = ObjectMapper()
        val jsonRequest = objectMapper.writeValueAsString(interest)

        //when //then
        mockMvc.perform(
            post("/api/v1/certificates/interests")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").isNotEmpty)
            .andExpect(jsonPath("energySource").value(interest.energySource.toString()))
            .andExpect(jsonPath("quantity").value(interest.quantity))
            .andExpect(jsonPath("buyerId").value(interest.buyerId))
            .andExpect(jsonPath("status").value(InterestStatus.OPEN.toString()));
    }

    @Test
    @DisplayName("should return all open interest")
    fun getAllOpenInterests_shouldReturnOpenInterests() {
        //given
        val mockCertificateInterests =
            mutableListOf(CertificateInterestResponseDTO(1L, EnergySource.SOLAR, 100, "2002", "", InterestStatus.OPEN))
        `when`(certificateService.getAllOpenInterests()).thenReturn(mockCertificateInterests)

        //when  //then
        mockMvc.perform(
            get("/api/v1/certificates/interests")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].energySource").value(EnergySource.SOLAR.toString()))
            .andExpect(jsonPath("$[0].quantity").value(100))
            .andExpect(jsonPath("$[0].buyerId").value("2002"))
            .andExpect(jsonPath("$[0].status").value(InterestStatus.OPEN.toString()));
    }

    @Test
    @DisplayName("should return all buyer open interest")
    fun getAllBuyerOpenInterestsByBuyerId_shouldReturnOpenInterests() {

        //given
        val buyerId = "2002"
        val mockCertificateInterests =
            mutableListOf(CertificateInterestResponseDTO(1L, EnergySource.SOLAR, 100, buyerId, "", InterestStatus.OPEN))
        `when`(certificateService.getInterestsByBuyerId(buyerId)).thenReturn(mockCertificateInterests)

        //when  //then
        mockMvc.perform(
            get("/api/v1/certificates/interests/buyer/{buyerId}", buyerId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].energySource").value(EnergySource.SOLAR.toString()))
            .andExpect(jsonPath("$[0].quantity").value(100))
            .andExpect(jsonPath("$[0].buyerId").value("2002"))
            .andExpect(jsonPath("$[0].status").value(InterestStatus.OPEN.toString()));
    }


    @Test
    @DisplayName("should return matched interest")
    fun matchCertificateInterest_shouldReturnClosedInterest() {
        ///given
        var matchInterestDTO = MatchInterestDTO(1L, "1001", arrayOf(2L))
        var mockCertificateInterest = MatchInterestResponseDTO(1L, EnergySource.SOLAR, 100, "2002", "1001", InterestStatus.CLOSED)

        `when`(certificateService.matchInterest(matchInterestDTO)).thenReturn(mockCertificateInterest);

        val objectMapper = ObjectMapper()
        val jsonRequest = objectMapper.writeValueAsString(matchInterestDTO)

        //when //then
        mockMvc.perform(
            put("/api/v1/certificates/interests/match")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("energySource").value(EnergySource.SOLAR.toString()))
            .andExpect(jsonPath("quantity").value(100))
            .andExpect(jsonPath("buyerId").value("2002"))
            .andExpect(jsonPath("sellerId").value("1001"))
            .andExpect(jsonPath("status").value(InterestStatus.CLOSED.toString()));
    }
}