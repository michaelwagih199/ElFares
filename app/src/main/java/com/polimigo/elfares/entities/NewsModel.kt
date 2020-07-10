package com.polimigo.elfares.entities
import com.google.gson.annotations.SerializedName

data class NewsModel (
	@SerializedName("details") val details : String,
	@SerializedName("link") val link : String,
	@SerializedName("pic") val pic : String,
	@SerializedName("title") val title : String
)