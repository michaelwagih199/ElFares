package com.polimigo.elfares.entities

data class MatchModel(
    var isSelected:Boolean = false,
    val channel: String,
    val createdAt: String,
    val day: String,
    val enabled: Boolean,
    val id: Int,
    val league: String,
    val link: String,
    val result: String,
    val teams: List<Team>,
    val time: String,
    val updatedAt: String
)