package me.olaosebikan.energy.marketplace.repository

import me.olaosebikan.energy.marketplace.dto.EnergySource
import me.olaosebikan.energy.marketplace.dto.InterestStatus
import me.olaosebikan.energy.marketplace.entity.CertificateInterest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class CertificateInterestRepositoryTest {

    @Autowired
    lateinit var testEntityManager : TestEntityManager

    @Autowired
    lateinit var certificateInterestRepository : CertificateInterestRepository;

    @Test
    @DisplayName("should return all open interest")
    fun getAllOpenInterests_shouldReturnAllOpenInterests(){
        //given
        var interest =  CertificateInterest(EnergySource.SOLAR,100,"2002","1001",InterestStatus.OPEN)
        var createdInterest = testEntityManager.persistAndFlush(interest)

        //when
        var  interestList = certificateInterestRepository.findAllOpenInterest();

        //then
        assertThat(interestList).hasSize(1);
        assertThat(interestList[0]).isEqualTo(interest);
    }

    @Test
    @DisplayName("should return all buyer interests by buyer id")
    fun getOwnerCertificatesByOwnerId_shouldReturnOwnerCertificate(){
        //given
        var buyerId : String = "1002";
        var interest =  CertificateInterest(EnergySource.WATER,100,buyerId,"1001",InterestStatus.OPEN)
        var createdInterest = testEntityManager.persistAndFlush(interest)

        //when
        var interestList = certificateInterestRepository.findByBuyerId(buyerId);

        //then
        assertThat(interestList).hasSize(1);
        assertThat(interestList[0]).isEqualTo(interest);
    }
}