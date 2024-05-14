package com.turgul.politicalspeech.service

import com.turgul.politicalspeech.dto.PoliticalSpeechStatisticsDto
import com.turgul.politicalspeech.repository.PoliticalSpeechEntity
import com.turgul.politicalspeech.repository.PoliticalSpeechRepository
import com.turgul.politicalspeech.repository.PoliticalSpeechResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.net.URL
import java.time.LocalDate

@Service
class PoliticalSpeechService(
    val politicalSpeechRepository: PoliticalSpeechRepository
) {

    @Value("\${political.speech.csv.file.delimiter}")
    var csvFileDelimiter: String = ";"

    @Value("\${political.speech.csv.urls.endpoint}")
    var csvUrlsEndpoint: String = ""

    fun processCsvFiles() {
        var response: String? = URL(csvUrlsEndpoint).readText()
        if (response?.startsWith("/evaluation?") == true) {
            response = response.replace("/evaluation?", "", true)

            val httpEndpoints = response.split("&", "=").filter { it.startsWith("http") }

            for (url in httpEndpoints) {
                parseAndSaveCSVFile(url)
            }

        } else {
            throw IllegalArgumentException("Could not get valid CSV file URLs from:$csvUrlsEndpoint")
        }

    }

    fun parseAndSaveCSVFile(url: String) {
        try {
            val csvData = URL(url).readText()
            val lines = csvData.lines()

            for (line in lines.drop(1)) {
                val speechEntity = createSpeechEntity(line)
                if (speechEntity != null) {
                    politicalSpeechRepository.save(speechEntity)
                }
            }
        } catch (ex: RuntimeException) {
            println("An exception occurred while getting/parsing CSV file. $ex")
        }
    }

    fun createSpeechEntity(csvLine: String): PoliticalSpeechEntity? {
        try {
            val values = csvLine.split(csvFileDelimiter)
            return PoliticalSpeechEntity(
                id = null,
                speaker = values[0].trim(),
                topic = values[1].trim().lowercase(),
                date = LocalDate.parse(values[2].trim()),
                words = Integer.parseInt(values[3].trim())
            )
        } catch (ex: RuntimeException) {
            println("An exception occurred while parsing CSV line: $csvLine. $ex")
        }
        return null
    }

    fun getStatistics(): PoliticalSpeechStatisticsDto {

        val mostSpeakersIn2013 =
            politicalSpeechRepository.getSpeakerWhoMadeTheMostSpeechesInGivenYear(
                LocalDate.of(2013, 1, 1),
                LocalDate.of(2013, 12, 31)
            )

        val mostSpeakersOnHomelandSecurity =
            politicalSpeechRepository.getSpeakerWhoMadeTheMostSpeechesOnGivenTopic("homeland security")
        val speakerOfFewestWords = politicalSpeechRepository.getSpeakerWhoMadeTheFewestWordsOverall()


        return PoliticalSpeechStatisticsDto(
            checkSpeakerResults(mostSpeakersIn2013),
            checkSpeakerResults(mostSpeakersOnHomelandSecurity),
            checkSpeakerResults(speakerOfFewestWords)
        )
    }

    fun checkSpeakerResults(speechResults: List<PoliticalSpeechResult>): String? {
        if (speechResults.isEmpty() || (speechResults.count() == 2 && speechResults[0].words == speechResults[1].words)) {
            return null
        }
        return speechResults[0].speaker
    }
}