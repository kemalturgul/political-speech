package com.turgul.politicalspeech.controller

import com.turgul.politicalspeech.PoliticalSpeechApplication
import com.turgul.politicalspeech.dto.PoliticalSpeechStatisticsDto
import com.turgul.politicalspeech.repository.PoliticalSpeechRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@DirtiesContext
@SpringBootTest(
    classes = [PoliticalSpeechApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = ["server.port=8082"]
)
@ActiveProfiles(profiles = ["test"])
class PoliticalSpeechIntegrationTest(
    @Autowired var restTemplate: TestRestTemplate,
    @Autowired val politicalSpeechRepository: PoliticalSpeechRepository
) {

    @BeforeEach
    fun cleanDatabase() {
        politicalSpeechRepository.deleteAll()
    }

    @Test
    fun returnsSpeechStatisticsSuccessfully() {
        processesPoliticalSpeechesSuccessfully()

        val response =
            this.restTemplate.getForEntity(
                "/v1/political-speeches/statistics",
                PoliticalSpeechStatisticsDto::class.java
            )
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(response.body).isEqualTo(
            PoliticalSpeechStatisticsDto(
                "Caesare Collins",
                "Arnold Babel",
                "Bernhard Belling"
            )
        )
    }

    @Test
    fun returnsSpeechStatisticsValuesAsNullWhenNoDataFound() {

        val response = this.restTemplate.getForEntity(
            "/v1/political-speeches/statistics",
            PoliticalSpeechStatisticsDto::class.java
        )
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(response.body).isEqualTo(
            PoliticalSpeechStatisticsDto(null, null, null)
        )
    }

    @Test
    fun processesPoliticalSpeechesSuccessfully() {
        val response = this.restTemplate.postForEntity(
            "/v1/political-speeches/start-processing",
            "",
            ResponseEntity::class.java
        )
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
    }

}