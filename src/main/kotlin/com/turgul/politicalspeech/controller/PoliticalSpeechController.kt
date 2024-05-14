package com.turgul.politicalspeech.controller

import com.turgul.politicalspeech.dto.PoliticalSpeechStatisticsDto
import com.turgul.politicalspeech.service.PoliticalSpeechService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/political-speeches")
class PoliticalSpeechController(val politicalSpeechService: PoliticalSpeechService) {

    @PostMapping("/start-processing")
    fun processPoliticalSpeeches(): ResponseEntity<Void> {
        println("Process political speech request received !!")

        politicalSpeechService.processCsvFiles()
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/statistics")
    fun statistics(): PoliticalSpeechStatisticsDto {
        println("Get statistics request received !!")

        return politicalSpeechService.getStatistics()
    }
}