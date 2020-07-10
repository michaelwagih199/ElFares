package com.polimigo.elfares.entities.matches
import com.google.gson.annotations.SerializedName

data class Teams (
	@SerializedName("team1") val team1 : Team1,
	@SerializedName("team2") val team2 : Team2
)