package com.polimigo.elfares.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.polimigo.elfares.R;

import java.net.URL;

public class PlayerActivity extends AppCompatActivity {
    private String urlStream;
    private VideoView myVideoView;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        myVideoView = (VideoView)this.findViewById(R.id.myVideoView);
        MediaController mc = new MediaController(this);
        myVideoView.setMediaController(mc);
        urlStream =getString(R.string.media_url_m3u8);

        myVideoView.setVideoURI(Uri.parse(urlStream));

    }



}