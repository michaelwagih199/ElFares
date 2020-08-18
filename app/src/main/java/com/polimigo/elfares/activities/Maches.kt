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
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.MultipleTypeAdapter
import com.polimigo.elfares.entities.MatchModel
import com.polimigo.elfares.entities.banner.BannerModel
import com.polimigo.elfares.service.SharedPrefrenceHelper
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Maches : AppCompatActivity(), View.OnClickListener {
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var mContext: Context
    var imagVMachesAds: ImageView? = null
    var MachesRecycle: RecyclerView? = null
    private lateinit var addImagUrl: String
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayMaches = ArrayList<MatchModel>()//Creating an empty arraylist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maches)
        MachesRecycle = findViewById(R.id.MachesRecycle) as RecyclerView
        imagVMachesAds = findViewById(R.id.imagVMachesAds)
        imagVMachesAds!!.setOnClickListener(this)
        mContext = this
        MachesRecycle!!.setHasFixedSize(true)
        // use a linear layout manager
        MachesRecycle?.adapter =
            MultipleTypeAdapter(this, arrayMaches, SharedPrefrenceHelper().getReview(mContext))
        MachesRecycle?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mContext = this
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()
        getMatches()
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
                    addImagUrl = it?.matchesModel?.link
                    imagVMachesAds?.let { it1 ->
                        Glide
                            .with(mContext) // give it the context
                            .load(it?.matchesModel?.pic) // load the image
                            .into(it1)
                    } // select the ImageView to load it into
                }
            }

            override fun onFailure(call: Call<List<BannerModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })

    }


    private fun getMatches() {
        val call: Call<List<MatchModel>> = ApiClient.getClient.getEnableMatches(true)
        call.enqueue(object : Callback<List<MatchModel>> {

            override fun onResponse(
                call: Call<List<MatchModel>>?,
                response: Response<List<MatchModel>>?
            ) {
                progerssProgressDialog.dismiss()
                arrayMaches.addAll(response!!.body()!!)
                Log.i("tag", "" + response!!.body()!!)
                MachesRecycle?.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<MatchModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })

    }


}