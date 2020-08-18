package com.polimigo.elfares.entities
import com.google.gson.annotations.SerializedName

data class ChannelsModel (
	@SerializedName("frequency") val frequency : String,
	@SerializedName("link") val link : String,
	@SerializedName("name") val name : String,
	@SerializedName("pic") val pic : String,
	@SerializedName("createdAt") val createdAt : String,
	@SerializedName("updatedAt") val updatedAt : String,
	@SerializedName("id") val id : Int
)
