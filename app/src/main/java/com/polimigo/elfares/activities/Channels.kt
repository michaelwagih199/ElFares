package com.polimigo.elfares.activities
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.DataChannelsAdpter
import com.polimigo.elfares.entities.AppSettingModel
import com.polimigo.elfares.entities.ChannelsModel
import com.polimigo.elfares.entities.banner.BannerModel
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Channels : AppCompatActivity(), View.OnClickListener,
    DataChannelsAdpter.OnItemChannelsClickListener {
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var mContext: Context
    private lateinit var addImagUrl: String
    var imagVchannelAds: ImageView? = null
    var recycleChannels: RecyclerView? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayChannels = ArrayList<ChannelsModel>()//Creating an empty arraylist
    private lateinit var mInterstitialAd: InterstitialAd
    var review: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)
        imagVchannelAds = findViewById(R.id.imagVChannelsAds)
        imagVchannelAds?.setOnClickListener(this)
        recycleChannels = findViewById(R.id.recycleNews) as RecyclerView
        recycleChannels!!.setHasFixedSize(true)
        // use a linear layout manager
        recycleChannels?.adapter = DataChannelsAdpter(arrayChannels, this, this)
        recycleChannels?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mContext = this
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()
        getChannel()
        getShareLink()

        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/8691691433"
        mInterstitialAd.adUnitId = getString(R.string.add_unit_old);
        mInterstitialAd.loadAd(AdRequest.Builder().build())

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.imagVChannelsAds ->{
                    Log.e("ram", "" + arrayBannerAdd)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(addImagUrl)
                    startActivity(i)
                }

            }
        }
    }

    private fun getChannel() {
        val call: Call<List<ChannelsModel>> = ApiClient.getClient.getChannels()
        call.enqueue(object : Callback<List<ChannelsModel>> {

            override fun onResponse(
                call: Call<List<ChannelsModel>>?,
                response: Response<List<ChannelsModel>>?
            ) {
                progerssProgressDialog.dismiss()
                arrayChannels.addAll(response!!.body()!!)
                Log.i("tag", "" + response!!.body()!!)
                recycleChannels?.adapter?.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<List<ChannelsModel>>?, t: Throwable?) {
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
                    addImagUrl = it?.channelsModel?.link
                    imagVchannelAds?.let { it1 ->
                        Glide
                            .with(mContext) // give it the context
                            .load(it?.channelsModel?.pic) // load the image
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

    fun toFrame(channelsModel: ChannelsModel) {
        var i = Intent(this, VedioView::class.java)
        i.putExtra("EXTRA_SESSION_ID", channelsModel.link)
        startActivity(i)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
        finish()
    }

    override fun onItemClicked(newsAdapter: ChannelsModel) {
        if (this!!.review!!) {
            dialog("Sorry live not response", "channel frequency :" + newsAdapter.frequency)
        } else if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                // Load the next interstitial.
                toFrame(newsAdapter)
            }
        }
    }


    fun getShareLink() {
        val call: Call<AppSettingModel> = ApiClient.getClient.getSetting()
        call.enqueue(object : Callback<AppSettingModel> {
            override fun onResponse(
                call: Call<AppSettingModel>?,
                response: Response<AppSettingModel>?
            ) {
                var appSettingFoo: AppSettingModel
                appSettingFoo = response!!.body()!!
                review = appSettingFoo.review
            }
            override fun onFailure(call: Call<AppSettingModel>, t: Throwable) {
            }

        })
    }

    fun dialog(title: String, message: String) {

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
        }

        val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


}