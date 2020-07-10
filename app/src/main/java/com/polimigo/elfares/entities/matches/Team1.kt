package com.polimigo.elfares.entities.matches

import com.google.gson.annotations.SerializedName


data class Team1 (

	@SerializedName("logo") val logo : String,
	@SerializedName("name") val name : String
)