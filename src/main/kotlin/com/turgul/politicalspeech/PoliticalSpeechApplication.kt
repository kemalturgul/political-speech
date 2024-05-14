package com.turgul.politicalspeech

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class PoliticalSpeechApplication

fun main(args: Array<String>) {
    runApplication<PoliticalSpeechApplication>(*args)
}

