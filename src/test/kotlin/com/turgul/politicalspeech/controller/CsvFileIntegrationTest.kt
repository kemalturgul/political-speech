package com.turgul.politicalspeech.controller

import com.turgul.politicalspeech.PoliticalSpeechApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatusCode
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    classes = [PoliticalSpeechApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext
@ActiveProfiles(profiles = ["test"])
class CsvFileIntegrationTest(@Autowired var restTemplate: TestRestTemplate) {

    @Test
    fun returnsCsvURlsSuccessfully() {
        val response = this.restTemplate.getForEntity("/csv-files/endpoints", String::class.java)
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(response.body).isEqualTo(
            "/evaluation?url1=http://localhost:8082/csv-files/file-1.csv&url2=http://localhost:8082/csv-files/file-2.csv"
        )
    }

    @Test
    fun returnsCsvFileContentSuccessfully() {
        val response = this.restTemplate.getForEntity("/csv-files/file-1.csv", String::class.java)
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(response.body).isEqualTo(
            "Speaker;Topic;Date;Words\nAlexander Abel; education policy; 2012-10-30; 5310\nBernhard Belling; coal subsidies; 2012-11-05; 1210\nCaesare Collins; coal subsidies; 2012-11-06; 1119\nAlexander Abel; homeland security; 2012-12-11; 911"
        )
    }

    @Test
    fun whenCouldNotFindFileThenReturnsInternalServerError() {
        val response = this.restTemplate.getForEntity("/csv-files/file-20.csv", String::class.java)
        assertThat(response).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(500))
    }


}