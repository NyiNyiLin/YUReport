package com.nyi.yureport.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nyi.yureport.R

import kotlinx.android.synthetic.main.activity_report.*
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.google.firebase.analytics.FirebaseAnalytics
import com.nyi.yureport.R.id.webview

class Report : AppCompatActivity() {

    private lateinit var myWebView : WebView
    private lateinit var dia : Dialog
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {
        fun newIntent(context: Context) : Intent {
            val intent = Intent(context, Report::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        setSupportActionBar(toolbar)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpProgressDialog()

        myWebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true

        /*myWebView.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })*/

        myWebView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                dia.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                hideProgressDialog()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                hideProgressDialog()
                Toast.makeText(baseContext, "Error in loading report form", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        loadLink()

    }

    private fun loadLink(){
        val list = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val link = p0.getValue(String::class.java)
                //Log.d("global", link)
                myWebView.loadUrl(link)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        }
        FirebaseDatabase.getInstance().getReference().child("v1").child("link").addValueEventListener(list)

    }

    fun setUpProgressDialog() {
        dia = Dialog(this)
        //dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(R.layout.custom_loading_layout)
        dia.setCancelable(false)

        val gifImageView : ImageView = dia.findViewById(R.id.custom_loading_imageView)

        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */
        val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)
        //...now load that gif which we put inside the drawble folder here with the help of Glide
        Glide.with(this)
            .load(R.drawable.loading_icon)
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .crossFade()
            .into(imageViewTarget)

    }

    fun hideProgressDialog() {
        if(dia.isShowing) dia.dismiss()
    }

}
