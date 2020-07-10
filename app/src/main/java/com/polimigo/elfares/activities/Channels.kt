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
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.*
import com.polimigo.elfares.entities.ChannelsModel
import com.polimigo.elfares.entities.banner.BannerModel
import com.squareup.picasso.Picasso
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Channels : AppCompatActivity() , View.OnClickListener , OnChannelsItemClickListener {
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var mContext: Context
    private lateinit var addImagUrl:String
    var imagVchannelAds: ImageView? = null
    var recycleChannels: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayChannels = ArrayList<ChannelsModel>()//Creating an empty arraylist
    private lateinit var adapter: RecyclerChannelsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)
        imagVchannelAds = findViewById(R.id.imagVChannelsAds)
        imagVchannelAds?.setOnClickListener(this)
        recycleChannels = findViewById(R.id.recycleNews) as RecyclerView
        recycleChannels!!.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager =  LinearLayoutManager(this);
        recycleChannels!!.setLayoutManager(mLayoutManager);
        adapter = RecyclerChannelsAdapter(this, arrayChannels, this)
        mContext = this
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.imagVChannelsAds ->{
                    Log.e("ram", ""+arrayBannerAdd)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(addImagUrl)
                    startActivity(i)
                }

            }
        }
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
                    addImagUrl = it?.channelsModel?.pic
                    Picasso
                        .with(mContext) // give it the context
                        .load(addImagUrl) // load the image
                        .into(imagVchannelAds) // select the ImageView to load it into
                }
            }

            override fun onFailure(call: Call<List<BannerModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })

    }

    override fun OnChannelsItemClickListener(channelsModel: ChannelsModel) {
        toFrame(channelsModel)
    }

    fun toFrame(channelsModel: ChannelsModel) {
        var i = Intent(this, ChannelsFrame::class.java)
        i.putExtra("ID", channelsModel.link)
        startActivity(i)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
    }

}