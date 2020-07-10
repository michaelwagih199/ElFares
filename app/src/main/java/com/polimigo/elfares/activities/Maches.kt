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
import com.polimigo.elfares.adapters.OnMatchesItemClickListener
import com.polimigo.elfares.adapters.RecyclerMachesAdapter
import com.polimigo.elfares.entities.banner.BannerModel
import com.polimigo.elfares.entities.matches.MachesModel
import com.squareup.picasso.Picasso
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Maches : AppCompatActivity() , View.OnClickListener , OnMatchesItemClickListener {
    lateinit var progerssProgressDialog: ProgressDialog
    private lateinit var mContext: Context
    var imagVMachesAds: ImageView? = null
    var MachesRecycle: RecyclerView? = null
    private lateinit var addImagUrl:String
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayMaches = ArrayList<MachesModel>()//Creating an empty arraylist
    private lateinit var adapter: RecyclerMachesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maches)
        MachesRecycle = findViewById(R.id.MachesRecycle) as RecyclerView
        imagVMachesAds =findViewById(R.id.imagVMachesAds)
        imagVMachesAds!!.setOnClickListener(this)
        MachesRecycle!!.setHasFixedSize(true);
        mLayoutManager = LinearLayoutManager(this);
        MachesRecycle!!.setLayoutManager(mLayoutManager)
        adapter = RecyclerMachesAdapter(this, arrayMaches, this)
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
                    addImagUrl = it?.matchesModel?.pic
                    Picasso
                        .with(mContext) // give it the context
                        .load(addImagUrl) // load the image
                        .into(imagVMachesAds) // select the ImageView to load it into
                }
            }

            override fun onFailure(call: Call<List<BannerModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })

    }


    override fun OnMatchesItemClickListener(machesModel: MachesModel) {

    }

}