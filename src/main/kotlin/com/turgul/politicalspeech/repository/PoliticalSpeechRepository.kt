package com.turgul.politicalspeech.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PoliticalSpeechRepository : JpaRepository<PoliticalSpeechEntity, Long> {

    @Query(
        value =
        "SELECT speaker, sum(words) as words FROM POLITICAL_SPEECH where date>= :startDate and date<=:endDate group by speaker order by words desc limit 2",
        nativeQuery = true
    )
    fun getSpeakerWhoMadeTheMostSpeechesInGivenYear(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<PoliticalSpeechResult>

    @Query(
        value =
        "SELECT speaker, sum(words) as words FROM POLITICAL_SPEECH where topic= :topic group by speaker order by words desc limit 2",
        nativeQuery = true
    )
    fun getSpeakerWhoMadeTheMostSpeechesOnGivenTopic(@Param("topic") topic: String): List<PoliticalSpeechResult>

    @Query(
        value =
        "SELECT speaker, sum(words) as words FROM POLITICAL_SPEECH group by speaker order by words limit 2",
        nativeQuery = true
    )
    fun getSpeakerWhoMadeTheFewestWordsOverall(): List<PoliticalSpeechResult>


}