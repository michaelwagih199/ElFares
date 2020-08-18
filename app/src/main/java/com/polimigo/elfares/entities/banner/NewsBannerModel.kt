package com.polimigo.elfares.entities.banner
import com.google.gson.annotations.SerializedName

data class NewsBannerModel (

	@SerializedName("link") val link : String,
	@SerializedName("pic") val pic : String
)