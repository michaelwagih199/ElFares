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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.database.*
import com.polimigo.elfares.R
import com.polimigo.elfares.adapters.OnItemClickListener
import com.polimigo.elfares.adapters.RecyclerAdapter
import com.polimigo.elfares.entities.BannerModel
import com.polimigo.elfares.entities.NewsModel
import com.squareup.picasso.Picasso


class News : AppCompatActivity(), View.OnClickListener, OnItemClickListener {
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var mContext: Context
    var imagVNewsAds: ImageView? = null
    var recycleNews: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    val arrayBannerAdd = ArrayList<BannerModel>()//Creating an empty arraylist
    val arrayNews = ArrayList<NewsModel>()//Creating an empty arraylist
    private lateinit var adapter: RecyclerAdapter

    lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = mFirebaseDatabase!!.getReference()

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
        adapter = RecyclerAdapter(this, arrayNews, this)
        mContext = this
        getBanners()
        getPostst()


    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.imagVNewsAds -> {
                    Log.e("ram", "" + arrayBannerAdd)
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
            databaseReference!!.child("news")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (issue in dataSnapshot.children) {
                        val note: NewsModel? =
                            issue.getValue(NewsModel::class.java)
                        // note.setUserId(us\);
                        arrayNews.add(note!!)
                        Log.i("tttt", "" + note);
                    }

                    //Log.e("tttt",arrayList.get(0).getName()+""+arrayList.get(1).getName());

//                    val customAdapter = NewsAdapter(mContext,arrayNews,)
                    recycleNews?.setAdapter(adapter)
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
                        .into(imagVNewsAds) // select the ImageView to load it into
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })
    }

    override fun onItemClicked(newsModel: NewsModel) {
//        Toast.makeText(this,"User name ${user.username} \n Phone:${user.phone}", Toast.LENGTH_LONG)
//            .show()

        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                // Load the next interstitial.
                toFrame(newsModel)
            }
        }
        /*

        Log.e("link", newsModel.link)
        var i = Intent(this, NewsFrame::class.java)
        i.putExtra("ID", newsModel.link)
        startActivity(i)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
    */
    }

    fun toFrame(newsModel: NewsModel) {
        var i = Intent(this, NewsFrame::class.java)
        i.putExtra("ID", newsModel.link)
        startActivity(i)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
    }


}