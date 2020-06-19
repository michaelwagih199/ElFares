package com.polimigo.elfares.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.BannerModel
import com.polimigo.elfares.entities.CommentModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.prompts.*
import java.util.*
import kotlin.collections.ArrayList

class Setting : AppCompatActivity(), View.OnClickListener {
    val arrayList = ArrayList<BannerModel>()//Creating an empty arraylist
    private lateinit var mContext: Context
    lateinit var imgVSettingAd: ImageView
    lateinit var btnShare: Button
    lateinit var btnMessage: Button
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
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
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = mFirebaseDatabase!!.getReference()
        getBanners()
        getShareLink()
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
            databaseReference!!.child("banners").child("setting")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val note: BannerModel? = dataSnapshot.getValue(BannerModel::class.java)
                    arrayList.add(note!!)
                    Picasso
                        .with(mContext) // give it the context
                        .load(note.pic) // load the image
                        .into(imgVSettingAd) // select the ImageView to load it into
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })
    }

    fun getShareLink() {
        val query =
            databaseReference!!.child("androidLink")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    shareLink = dataSnapshot.getValue().toString()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })
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
        try {
            val commentsPojoAdd = CommentModel(commentContent)
            databaseReference!!.child("problems").child(nodeId).setValue(commentContent)
            toastMessage("تم الحفظ")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
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