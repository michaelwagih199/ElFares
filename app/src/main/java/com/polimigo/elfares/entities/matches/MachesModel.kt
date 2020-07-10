package com.polimigo.elfares.entities.matches

import com.google.gson.annotations.SerializedName


data class MachesModel (

	@SerializedName("channel") val channel : String,
	@SerializedName("day") val day : String,
	@SerializedName("enabled") val enabled : Boolean,
	@SerializedName("league") val league : String,
	@SerializedName("link") val link : String,
	@SerializedName("result") val result : String,
	@SerializedName("teams") val teams : Teams,
	@SerializedName("time") val time : String
)