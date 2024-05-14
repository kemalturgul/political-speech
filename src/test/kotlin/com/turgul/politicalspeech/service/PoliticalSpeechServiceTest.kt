package com.turgul.politicalspeech.service

import com.turgul.politicalspeech.repository.PoliticalSpeechRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.net.MalformedURLException
import java.net.URL
import java.time.LocalDate

class PoliticalSpeechServiceTest {

    private val politicalSpeechRepository: PoliticalSpeechRepository = mock(PoliticalSpeechRepository::class.java)
    private val politicalSpeechService: PoliticalSpeechService = PoliticalSpeechService(politicalSpeechRepository)

    @Test
    fun processCsvFilesThrowsExceptionForInvalidURL() {
        politicalSpeechService.csvUrlsEndpoint = "invalid-url"
        val mockedUrl = mock(URL::class.java)
        `when`(mockedUrl.openStream()).thenThrow(MalformedURLException())

        val exception = assertThrows<MalformedURLException> {
            politicalSpeechService.processCsvFiles()
        }
        assertEquals("no protocol: invalid-url", exception.message)
    }

    @Test
    fun createsSpeechEntitySuccessfullyForValidLine() {
        val csvLine = "Speaker1;Topic1;2023-05-12;100"
        val speechEntity = politicalSpeechService.createSpeechEntity(csvLine)

        assertThat(speechEntity).isNotNull
        assertEquals("Speaker1", speechEntity!!.speaker)
        assertEquals("topic1", speechEntity.topic)
        assertEquals(LocalDate.of(2023, 5, 12), speechEntity.date)
        assertEquals(100, speechEntity.words)
    }

    @Test
    fun createSpeechEntityReturnsNullWhenExceptionOccurred() {
        val csvLine = "Invalid line"
        val speechEntity = politicalSpeechService.createSpeechEntity(csvLine)

        assertNull(speechEntity)
    }

}