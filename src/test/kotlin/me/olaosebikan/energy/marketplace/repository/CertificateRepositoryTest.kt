package me.olaosebikan.energy.marketplace.repository

import me.olaosebikan.energy.marketplace.dto.EnergySource
import me.olaosebikan.energy.marketplace.entity.Certificate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate


@DataJpaTest
class CertificateRepositoryTest {

    @Autowired
    lateinit var testEntityManager : TestEntityManager

    @Autowired
    lateinit var certificateRepository : CertificateRepository;

    @Test
    @DisplayName("should return all owner certificate by own id")
    fun getCertificatesByOwnerId_shouldReturnCertificates(){
        //given
        var ownerId : String = "1001";
        var certificate =  Certificate(ownerId,EnergySource.SOLAR,100,"First Dutch", LocalDate.parse("2021-10-14"))
        var createdCertificate = testEntityManager.persistAndFlush(certificate)

        //when
         var certificates = certificateRepository.findByOwnerId(ownerId);

        //then
        assertThat(certificates).hasSize(1);
        assertThat(certificates[0]).isEqualTo(certificate);
    }
}