package kotlincodes.com.retrofitwithkotlin.retrofit

import com.polimigo.elfares.entities.banner.BannerModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("banners")
    fun getBanners(): Call<List<BannerModel>>

    @GET("banners")
    fun getSetting(): Call<BannerModel>


}