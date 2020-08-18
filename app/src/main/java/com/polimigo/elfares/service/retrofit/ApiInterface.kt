package kotlincodes.com.retrofitwithkotlin.retrofit

import com.polimigo.elfares.entities.*
import com.polimigo.elfares.entities.banner.BannerModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("banners")
    fun getBanners(): Call<List<BannerModel>>

    @GET("news")
    fun getNews(): Call<List<NewsModel>>

    @GET("channels")
    fun getChannels(): Call<List<ChannelsModel>>

    @GET("app-settings")
    fun getSetting(): Call<AppSettingModel>

    @Headers("Content-Type: application/json")
    @POST("issues")
    fun postIssues(@Body body: IssusModel): Call<IssuesResponseModel>

    @GET("matchs")
    fun getEnableMatches( @Query("enabled") aParam: Boolean?): Call<List<MatchModel>>
}

