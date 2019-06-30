package io.github.pps5.kakaosampleapp.repository.entity

data class SearchResponse (
    val resultsReturned: Int,
    val resultsAvailable: Int,
    val events: List<Event>
)

data class Event (
    val eventId: Int,
    val title: String,
    val catch: String,
    val description: String,
    val eventUrl: String,
    val hashTag: String,
    val startedAt: String,
    val endedAt: String
)