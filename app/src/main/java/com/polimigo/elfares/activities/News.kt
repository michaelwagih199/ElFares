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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.NewsAdapter
import com.polimigo.elfares.adapters.OnItemNewsClickListener
import com.polimigo.elfares.entities.NewsModel
import com.polimigo.elfares.entities.banner.BannerModel
import com.squareup.picasso.Picasso
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class News : AppCompatActivity(), View.OnClickListener, OnItemNewsClickListener {
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var addImagUrl:String

    private lateinit var mContext: Context
    var imagVNewsAds: ImageView? = null
    var recycleNews: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayNews = ArrayList<NewsModel>()//Creating an empty arraylist
    private lateinit var adapter: NewsAdapter
    lateinit var mAdView: AdView
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
        recycleNews!!.setHasFixedSize(true);
        mLayoutManager = LinearLayoutManager(this);
        recycleNews!!.setLayoutManager(mLayoutManager)
        adapter = NewsAdapter(this, arrayNews, this)
        mContext = this
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()
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
                    addImagUrl = it?.newsModel?.pic
                    Picasso
                        .with(mContext) // give it the context
                        .load(addImagUrl) // load the image
                        .into(imagVNewsAds) // select the ImageView to load it into
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


    override fun onItemClicked(newsModel: NewsModel) {
        toFrame(newsModel)
    }

    fun toFrame(newsModel: NewsModel) {
        var i = Intent(this, NewsFrame::class.java)
        i.putExtra("ID", newsModel.link)
        startActivity(i)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
    }


}