package com.turgul.politicalspeech

import com.turgul.politicalspeech.controller.CsvFileController
import com.turgul.politicalspeech.controller.PoliticalSpeechController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class PoliticalSpeechApplicationTests {

    @Autowired
    lateinit var politicalSpeechController: PoliticalSpeechController

    @Autowired
    lateinit var csvFileController: CsvFileController


    @Test
    fun contextLoads() {
        assertThat(politicalSpeechController).isNotNull
        assertThat(csvFileController).isNotNull
    }

}
