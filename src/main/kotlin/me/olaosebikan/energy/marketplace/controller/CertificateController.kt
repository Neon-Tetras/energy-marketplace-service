package me.olaosebikan.energy.marketplace.controller

import io.swagger.annotations.Api;
import me.olaosebikan.energy.marketplace.dto.*
import me.olaosebikan.energy.marketplace.service.CertificateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/certificates")
@Api(value = "Energy Certificate Marketplace Api operations")
class CertificateController {

    @Autowired
    lateinit var certificateService : CertificateService;

    @PostMapping("/")
    fun createCertificate(@RequestBody certificateDTO: CertificateDTO) : ResponseEntity<CertificateResponseDTO> {
        var response = certificateService.createCertificate(certificateDTO);
        return ResponseEntity<CertificateResponseDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping("/owner/{ownerId}")
    fun getCertificateByOwnerId(@PathVariable("ownerId") ownerId: String) : ResponseEntity<List<CertificateResponseDTO>> {
        var response  = certificateService.getCertificatesByOwnerId(ownerId);
        return ResponseEntity<List<CertificateResponseDTO>>(response, HttpStatus.OK);
    }

    @PostMapping("/interests")
    fun createInterest(@RequestBody certificateInterest: CertificateInterestDTO) : ResponseEntity<CertificateInterestResponseDTO> {
        var response = certificateService.createInterest(certificateInterest);
        return ResponseEntity<CertificateInterestResponseDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping("/interests")
    fun getAllOpenInterest() : ResponseEntity<List<CertificateInterestResponseDTO>> {
        var response =  certificateService.getAllOpenInterests();
        return ResponseEntity<List<CertificateInterestResponseDTO>>(response, HttpStatus.OK);
    }

    @PutMapping("/interests/match")
    fun matchInterest(@RequestBody matchInterestDTO: MatchInterestDTO) : ResponseEntity<MatchInterestResponseDTO> {
        var response = certificateService.matchInterest(matchInterestDTO);
        return ResponseEntity<MatchInterestResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/interests/buyer/{buyerId}")
    fun getBuyerInterestByBuyerId(@PathVariable("buyerId") buyerId: String) : ResponseEntity<List<CertificateInterestResponseDTO>> {
        var response  = certificateService.getInterestsByBuyerId(buyerId);
        return ResponseEntity<List<CertificateInterestResponseDTO>>(response, HttpStatus.OK);
    }

}