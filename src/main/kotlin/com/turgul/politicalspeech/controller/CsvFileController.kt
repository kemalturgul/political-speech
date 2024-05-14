package com.turgul.politicalspeech.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.File

@RestController
@RequestMapping("/csv-files")
class CsvFileController {

    @GetMapping("/endpoints")
    fun endpoints(): ResponseEntity<String> {
        println("GET CSV File URLs request received !!")

        val content =
            "/evaluation?url1=http://localhost:8082/csv-files/file-1.csv&url2=http://localhost:8082/csv-files/file-2.csv"

        return ResponseEntity.ok().body(content)
    }

    @GetMapping("/{fileName}")
    fun fileContent(@PathVariable fileName: String): ResponseEntity<String> {
        println("GET CSV File content request received for fileName:$fileName")

        val file = File("src/test/resources/$fileName")
        val content = file.inputStream().bufferedReader().use(BufferedReader::readText)

        return ResponseEntity.ok().body(content)
    }
}