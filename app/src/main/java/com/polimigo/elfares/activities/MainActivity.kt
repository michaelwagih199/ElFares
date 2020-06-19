
package com.polimigo.elfares.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.BannerModel
import com.polimigo.elfares.entities.CommentModel
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val arrayList = ArrayList<BannerModel>()//Creating an empty arraylist
    private lateinit var mContext: Context
    var imgVmach: ImageView? = null
    lateinit var imgVsetting: ImageView
    var imgVchannel: ImageView? = null
    var imgVnews: ImageView? = null
    var imgVhomeAd: ImageView? = null
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
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
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = mFirebaseDatabase!!.getReference()
        getBanners()
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
                    val url = arrayList.get(0).link
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }

            }
        }
    }


    fun getBanners() {
        val query =
            databaseReference!!.child("banners").child("home")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val note: BannerModel? = dataSnapshot.getValue(BannerModel::class.java)
                    arrayList.add(note!!)
                    Picasso
                        .with(mContext) // give it the context
                        .load(note?.pic) // load the image
                        .into(imgVhomeAd) // select the ImageView to load it into
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })
    }





}
