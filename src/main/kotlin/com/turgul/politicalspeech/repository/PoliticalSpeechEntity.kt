package com.turgul.politicalspeech.repository

import jakarta.persistence.*
import java.time.LocalDate

@Table(
    name = "political_speech",
    indexes = [Index(name = "topic_idx", columnList = "topic"), Index(name = "date_idx", columnList = "date")]
)
@Entity
data class PoliticalSpeechEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    val speaker: String,
    val topic: String,
    val date: LocalDate,
    val words: Int
)
