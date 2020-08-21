package com.polimigo.elfares.activities
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.polimigo.elfares.R


class vedioPlayer : AppCompatActivity() {
    private var videoView: VideoView? = null
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio_player)
        mContext = this

    }

}