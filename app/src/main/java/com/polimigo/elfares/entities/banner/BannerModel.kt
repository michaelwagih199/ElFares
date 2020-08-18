package com.polimigo.elfares.entities.banner

import com.google.gson.annotations.SerializedName

data class BannerModel (

	@SerializedName("channels") val channelsModel : ChannelsBannersModel,
	@SerializedName("home") val homeModel : HomeModel,
	@SerializedName("matches") val matchesModel : MatchesModel,
	@SerializedName("news") val newsBannerModel : NewsBannerModel,
	@SerializedName("settings") val settingsModel : SettingsModel,
	@SerializedName("createdAt") val createdAt : String,
	@SerializedName("updatedAt") val updatedAt : String,
	@SerializedName("id") val id : Int
)
