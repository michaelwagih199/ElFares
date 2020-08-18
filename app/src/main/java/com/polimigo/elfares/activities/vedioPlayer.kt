package com.polimigo.elfares.activities
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.polimigo.elfares.R


class vedioPlayer : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    private var videoView: VideoView? = null
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio_player)
        mContext = this

    }

    private fun setupVideoView() {
        // Make sure to use the correct VideoView import
        videoView = findViewById<View>(R.id.video_view_new) as VideoView
        videoView!!.setOnPreparedListener(this)

        //For now we just picked an arbitrary item to play
        videoView!!.setVideoURI(Uri.parse(getString(R.string.media_url_m3u8)))
    }

    override fun onPrepared(mp: MediaPlayer?) {
        //Starts the video playback as soon as it is ready
        videoView?.start()
    }

    override fun onPause() {
        super.onPause()
        //Pause Video Playback
        videoView?.pause()
    }


}