package com.polimigo.elfares.activities
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.DataNewsAdpter
import com.polimigo.elfares.entities.NewsModel
import com.polimigo.elfares.entities.banner.BannerModel
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class News : AppCompatActivity(), View.OnClickListener , DataNewsAdpter.OnItemNewsClickListener {
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var addImagUrl:String
    private lateinit var mContext: Context
    var imagVNewsAds: ImageView? = null
    var recycleNews: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayNews = ArrayList<NewsModel>()//Creating an empty arraylist
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        //mobile add
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        imagVNewsAds = findViewById(R.id.imagVNewsAds)
        imagVNewsAds?.setOnClickListener(this)
        recycleNews = findViewById(R.id.recycleNews) as RecyclerView

        recycleNews?.adapter = DataNewsAdpter(arrayNews, this,this)
        recycleNews?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mContext = this
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()
        getNews()
    }

    private fun getNews() {
        val call: Call<List<NewsModel>> = ApiClient.getClient.getNews()
        call.enqueue(object : Callback<List<NewsModel>> {

            override fun onResponse(call: Call<List<NewsModel>>?, response: Response<List<NewsModel>>?) {
                progerssProgressDialog.dismiss()
                arrayNews.addAll(response!!.body()!!)

                recycleNews?.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<NewsModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })

    }

    private fun getBanner1() {
        val call: Call<List<BannerModel>> = ApiClient.getClient.getBanners()
        call.enqueue(object : Callback<List<BannerModel>> {
            override fun onResponse(
                call: Call<List<BannerModel>>?,
                response: Response<List<BannerModel>>?
            ) {
                progerssProgressDialog.dismiss()
                arrayBannerAdd.addAll(response!!.body()!!)
                arrayBannerAdd.forEach {
                    addImagUrl = it?.newsBannerModel?.link
                    imagVNewsAds?.let { it1 ->
                        Glide
                            .with(mContext) // give it the context
                            .load(it?.newsBannerModel?.pic) // load the image
                            .error(R.drawable.ic_baseline_error_24)
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .into(it1)
                    } // select the ImageView to load it into
                }
            }

            override fun onFailure(call: Call<List<BannerModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.imagVNewsAds -> {
                    Log.e("ram", "" + arrayBannerAdd)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(addImagUrl)
                    startActivity(i)
                }
            }
        }
    }

    fun toFrame(newsModel: NewsModel) {
        var i = Intent(this, NewsFrame::class.java)
        i.putExtra("ID", newsModel.link)
        startActivity(i)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
    }

    override fun onItemClicked(newsAdapter: NewsModel) {
        toFrame(newsAdapter)
    }


}