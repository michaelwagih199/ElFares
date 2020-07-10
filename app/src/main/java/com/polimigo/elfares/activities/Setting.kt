package com.polimigo.elfares.activities
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.banner.BannerModel
import com.squareup.picasso.Picasso
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Setting : AppCompatActivity(), View.OnClickListener {
    lateinit var progerssProgressDialog: ProgressDialog

    val arrayList = ArrayList<BannerModel>()//Creating an empty arraylist
    private lateinit var mContext: Context
    lateinit var imgVSettingAd: ImageView
    private lateinit var addImagUrl:String
    lateinit var btnShare: Button
    lateinit var btnMessage: Button
    lateinit var shareLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        mContext = this
        imgVSettingAd = findViewById(R.id.imagVSettingAds)
        imgVSettingAd.setOnClickListener(this)
        btnMessage = findViewById(R.id.btnMessage)
        btnShare = findViewById(R.id.btnShareApp)
        btnMessage.setOnClickListener(this)
        btnShare.setOnClickListener(this)
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getBanner1()
        getShareLink()
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
                    addImagUrl = it?.newsModel?.pic
                    Picasso
                        .with(mContext) // give it the context
                        .load(addImagUrl) // load the image
                        .into(imgVSettingAd) // select the ImageView to load it into
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
                R.id.btnMessage -> {
                    AddCommentDialog()
                }

                R.id.btnShareApp -> {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        shareLink
                    )
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                }

                R.id.imagVSettingAds -> {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(addImagUrl)
                    startActivity(i)
                }
            }
        }
    }


    fun getShareLink() {

    }


    fun AddCommentDialog() {
        // get prompts.xml view
        val li = LayoutInflater.from(this)
        val promptsView = li.inflate(R.layout.prompts, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView)
        val userInput = promptsView
            .findViewById(R.id.editTextDialogUserInput) as EditText
        // set dialog message
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("أرسل") { dialog, id -> // get user input and set it to result
                // edit text
                try {
                    addPost(generateCode(), userInput.text.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton(
                "إلغاء"
            ) { dialog, id -> dialog.cancel() }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()
        // show it
        alertDialog.show()
    }

    fun addPost(nodeId: String, commentContent: String) {

    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun generateCode(): String{
        // Creating a random UUID (Universally unique identifier).
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }
}