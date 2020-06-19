package com.polimigo.elfares.activities

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
import com.google.firebase.database.*
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.ChannelsAdapter
import com.polimigo.elfares.entities.BannerModel
import com.polimigo.elfares.entities.CommentModel
import com.polimigo.elfares.entities.ChannelsModel
import com.squareup.picasso.Picasso

class Channels : AppCompatActivity() , View.OnClickListener{

    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var mContext: Context
    var imagVchannelAds: ImageView? = null
    var recycleChannels: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayChannels = ArrayList<ChannelsModel>()//Creating an empty arraylist

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = mFirebaseDatabase!!.getReference()
        imagVchannelAds = findViewById(R.id.imagVChannelsAds)
        imagVchannelAds?.setOnClickListener(this)
        recycleChannels = findViewById(R.id.recycleNews) as RecyclerView
        recycleChannels!!.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager =  LinearLayoutManager(this);
        recycleChannels!!.setLayoutManager(mLayoutManager);
        mContext = this
        getBanners()
        getPostst()

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.imagVChannelsAds ->{
                    Log.e("ram", ""+arrayBannerAdd)
                    val url = arrayBannerAdd.get(0).link
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }

            }
        }
    }



    fun getPostst() {
        val query =
            databaseReference!!.child("channels")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (issue in dataSnapshot.children) {
                        val note: ChannelsModel? =
                            issue.getValue(ChannelsModel::class.java)
                        // note.setUserId(us\);
                        arrayChannels.add(note!!)
                        Log.i("tttt",""+note);
                    }

                    //Log.e("tttt",arrayList.get(0).getName()+""+arrayList.get(1).getName());
                    val customAdapter =
                        ChannelsAdapter(applicationContext, arrayChannels)
                    recycleChannels?.setAdapter(customAdapter)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })
    }




    fun getBanners() {
        val query =
            databaseReference!!.child("banners").child("channels")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val note: BannerModel? = dataSnapshot.getValue(BannerModel::class.java)
                    arrayBannerAdd.add(note!!)
                    Picasso
                        .with(mContext) // give it the context
                        .load(note?.pic) // load the image
                        .into(imagVchannelAds) // select the ImageView to load it into
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })
    }

}