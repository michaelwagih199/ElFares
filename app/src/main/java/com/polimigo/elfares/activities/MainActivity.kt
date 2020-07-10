
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
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.banner.BannerModel
import com.squareup.picasso.Picasso
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val arrayList = ArrayList<BannerModel>()//Creating an empty arraylist
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var mContext: Context
    private lateinit var addImagUrl:String
    var imgVmach: ImageView? = null
    lateinit var imgVsetting: ImageView
    var imgVchannel: ImageView? = null
    var imgVnews: ImageView? = null
    var imgVhomeAd: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        imgVchannel = findViewById(R.id.imgVchannel)
        imgVsetting = findViewById(R.id.imgVsetting)
        imgVsetting.setOnClickListener(this)
        imgVchannel!!.setOnClickListener(this)
        imgVmach = findViewById(R.id.imgVmach)
        imgVmach!!.setOnClickListener(this)
        imgVnews = findViewById(R.id.imgVnews)
        imgVnews!!.setOnClickListener(this)
        imgVhomeAd = findViewById(R.id.imgVhomeAd)
        imgVhomeAd!!.setOnClickListener(this)

        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {

                R.id.imgVchannel -> {

                    startActivity(Intent(this, Channels::class.java))
                    overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)

                }

                R.id.imgVmach -> {
                    startActivity(Intent(this, Maches::class.java))
                    overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
                }
                R.id.imgVnews -> {
                    startActivity(Intent(this, News::class.java))
                    overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
                }
                R.id.imgVsetting -> {
                    startActivity(Intent(this, Setting::class.java))
                    overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
                }

                R.id.imgVhomeAd -> {
                    Log.e("ram", "" + arrayList)
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
                arrayList.addAll(response!!.body()!!)
                arrayList.forEach {
                    addImagUrl = it?.homeModel?.pic
                    Picasso
                        .with(mContext) // give it the context
                        .load(addImagUrl) // load the image
                        .into(imgVhomeAd) // select the ImageView to load it into
                }
            }

            override fun onFailure(call: Call<List<BannerModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })



    }


}
