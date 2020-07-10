package com.polimigo.elfares.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.polimigo.elfares.R

class NewsFrame : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var url:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_frame)
        webView = findViewById(R.id.webView)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        val bundle: Bundle? = intent.extras
        if (bundle != null)
            url = bundle.getString("ID").toString()
        webView.loadUrl(url)

    }
}